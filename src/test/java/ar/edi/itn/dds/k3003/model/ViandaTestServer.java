package ar.edi.itn.dds.k3003.model;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.clients.LogisticaProxy;
import ar.edu.utn.dds.k3003.clients.ViandasProxy;
import ar.edu.utn.dds.k3003.facades.FachadaLogistica;
import ar.edu.utn.dds.k3003.facades.FachadaViandas;
import ar.edu.utn.dds.k3003.facades.dtos.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.mockito.Mock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static ar.edu.utn.dds.k3003.app.WebApp.createObjectMapper;
import static java.lang.Long.parseLong;
import static org.mockito.Mockito.when;

public class ViandaTestServer {
   /*static ObjectMapper objectMapper = createObjectMapper();
    static ViandasProxy fachadaViandas = new ViandasProxy(objectMapper);
    static LogisticaProxy fachadaLogistica = new LogisticaProxy(objectMapper);*/


    static Fachada fColaboradores = new Fachada();

    public static FachadaViandas fachadaViandas;
    //con o sin "@Mock" anda igual
    //sin "static" no me lo toma el método

    static FachadaLogistica fachadaLogistica;

    public static void main(String[] args) throws Exception {
        var env = System.getenv();

        var port = Integer.parseInt(env.getOrDefault("PORT", "8081"));

        var app = Javalin.create().start(port);

        app.get("/viandas/search/findByColaboradorIdAndAnioAndMes", ViandaTestServer::obtenerViandasColaborador);

        app.get("/traslados/search/findByColaboradorId", ViandaTestServer::obtenerTrasladosColaborador);

        app.get("/viandas/{qr}", ViandaTestServer::obtenerVianda);
    }

    private static void obtenerVianda(Context context) {

        var qr = context.pathParam("qr");
        if (qr.equals("unQRQueExiste")) {
            var viandaDTO1 = new ViandaDTO(qr, LocalDateTime.now(), EstadoViandaEnum.PREPARADA, 2L, 1);
            viandaDTO1.setId(14L);
            context.json(viandaDTO1);
        } else {
            context.result("Vianda no encontrada: " + qr);
            context.status(HttpStatus.NOT_FOUND);
        }
    }

    private static void obtenerViandasColaborador(Context context) {

        fColaboradores.setViandasProxy(fachadaViandas);
        var colaboradorId = parseLong(context.queryParam("colaboradorId"));
        var mes =  Integer.valueOf(context.queryParam("mes"));
        var anio = Integer.valueOf(context.queryParam("anio"));

        try{
           List<ViandaDTO> viandas = fachadaViandas.viandasDeColaborador(colaboradorId,mes,anio);
            context.json(viandas);
        } catch (NoSuchElementException ex) {
            context.result("Viandas no encontradas de " + colaboradorId);
            context.status(HttpStatus.NOT_FOUND);
        }
    }
    private static void obtenerTrasladosColaborador(Context context) {
        fColaboradores.setLogisticaProxy(fachadaLogistica);
        var colaboradorId = parseLong(context.queryParam("colaboradorId"));
        try{
            List<TrasladoDTO> viandas = fachadaLogistica.trasladosDeColaborador(colaboradorId,1,2024);
            context.json(viandas);

        } catch (NoSuchElementException ex) {
            context.result("Viandas no encontradas de " + colaboradorId);
            context.status(HttpStatus.NOT_FOUND);
        }
    }
}

