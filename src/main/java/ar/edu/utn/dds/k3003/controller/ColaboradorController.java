package ar.edu.utn.dds.k3003.controller;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.facades.dtos.ColaboradorDTO;
import ar.edu.utn.dds.k3003.facades.dtos.FormaDeColaborarEnum;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.eclipse.jetty.http.HttpTester;

import javax.lang.model.type.ArrayType;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class ColaboradorController {
    private final Fachada fachada;

    public ColaboradorController(Fachada fachada) {
        this.fachada = fachada;
    }

    public void agregar(Context context) {
        var colaboradorDTO = context.bodyAsClass(ColaboradorDTO.class);
        var colaboradorDTORta = this.fachada.agregar(colaboradorDTO);
        context.json(colaboradorDTORta);
        context.status(HttpStatus.CREATED);
        context.result("Colaborador agregado correctamente");
    }

    public void obtener(Context context) {
        var id = context.pathParamAsClass("id", Long.class).get();
        try {
            var colaboradorDTO = this.fachada.buscarXId(id);
            context.json(colaboradorDTO);
            context.status(HttpStatus.FOUND);

        } catch (NoSuchElementException ex) {
            context.result(ex.getLocalizedMessage());
            context.status(HttpStatus.NOT_FOUND);
        }
    }

    public void modificar(Context context) { //revisar
        var id = context.pathParamAsClass("id", Long.class).get();
        var forma = context.bodyAsClass(FormaDeColaborarEnum.class);//(FormaDeColaborarEnum.class);
        try{ var colaboradorDTO = this.fachada.modificar(id, List.of(forma));
        context.json(colaboradorDTO);
            context.status(HttpStatus.OK);
            context.result("Colaborador modificado correctamente");
        } catch (NoSuchElementException ex) {
            context.result(ex.getLocalizedMessage());
            context.status(HttpStatus.NOT_FOUND);
        }
    }

    public void puntos(Context context) {
        var id = context.pathParamAsClass("id", Long.class).get();
            try {var puntosColaborador = this.fachada.puntos(id);
            context.json(puntosColaborador);
            context.status(HttpStatus.OK);
            } catch (NoSuchElementException ex) {
                context.result(ex.getLocalizedMessage());
                context.status(HttpStatus.NOT_FOUND);
            }
    }

    public void actualizarPuntos(Context context) {
        //ArrayList<Double> puntos = context.bodyStreamAsClass(ArrayList.class);
        Double pesosDonados = context.attribute("pesosDonados");//puntos.get(0);
        Double viandasDistribuidas = context.attribute("viandasDistribuidas");
        Double viandasDonadas= context.attribute("viandasDonadas");
        Double tarjetasRepartidas= context.attribute("tarjetasRepartidas");
        Double heladerasActivas= context.attribute("heladerasActivas");
        /*Double viandasDistribuidas = puntos.get(1);
        Double viandasDonadas= puntos.get(2);
        Double tarjetasRepartidas= puntos.get(3);
        Double heladerasActivas= puntos.get(4);*/
        try {this.fachada.actualizarPesosPuntos(pesosDonados,
                viandasDistribuidas,
                viandasDonadas,
                tarjetasRepartidas,
                heladerasActivas);

            context.result("Puntos actualizados");
            context.status(HttpStatus.OK);

            }
        catch (NoSuchElementException ex) {
            context.result(ex.getLocalizedMessage());
            context.status(HttpStatus.NOT_ACCEPTABLE);//?
        }
    }
}
