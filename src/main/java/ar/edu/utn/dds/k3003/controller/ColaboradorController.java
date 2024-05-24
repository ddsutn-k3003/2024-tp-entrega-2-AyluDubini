package ar.edu.utn.dds.k3003.controller;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.facades.dtos.ColaboradorDTO;
import ar.edu.utn.dds.k3003.facades.dtos.FormaDeColaborarEnum;
import ar.edu.utn.dds.k3003.model.PuntosBody;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.List;
import java.util.NoSuchElementException;


public class ColaboradorController {
    private final Fachada fachada;

    public ColaboradorController(Fachada fachada) {
        this.fachada = fachada;
    }

    public void agregar(Context context) {
        var colaboradorDTO = context.bodyAsClass(ColaboradorDTO.class);
        var colaboradorDTORta = this.fachada.agregar(colaboradorDTO);
        context.json(colaboradorDTORta);
    }

    public void obtener(Context context) {
        var id = context.pathParamAsClass("id", Long.class).get();
        try {
            var colaboradorDTO = this.fachada.buscarXId(id);
            context.json(colaboradorDTO);
        } catch (NoSuchElementException ex) {
            context.result(ex.getLocalizedMessage());
            context.status(HttpStatus.NOT_FOUND);
        }
    }

    public void modificar(Context context) { //revisar
        var id = context.pathParamAsClass("id", Long.class).get();
        var forma = context.bodyAsClass(FormaDeColaborarEnum.class);
        try{
            var colaboradorDTO = this.fachada.modificar(id, List.of(forma));
            context.json(colaboradorDTO);
        } catch (NoSuchElementException ex) {
            context.result(ex.getLocalizedMessage());
            context.status(HttpStatus.NOT_FOUND);
        }
    }

    public void puntos(Context context) {
        var id = context.pathParamAsClass("id", Long.class).get();
            try {var puntosColaborador = this.fachada.puntos(id);
            context.json(puntosColaborador);
            } catch (NoSuchElementException ex) {
                context.result(ex.getLocalizedMessage());
                context.status(HttpStatus.NOT_FOUND);
            }
    }

    public void actualizarPuntos(Context context) {
        PuntosBody puntos = context.bodyAsClass(PuntosBody.class);
        Double pesosDonados = puntos.getPesosDonados();
        Double viandasDistribuidas = puntos.getViandasDistribuidas();//context.attribute("viandasDistribuidas");
        Double viandasDonadas= puntos.getViandasDonadas();
        Double tarjetasRepartidas= puntos.getTarjetasRepartidas();
        Double heladerasActivas= puntos.getHeladerasActivas();
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
            context.status(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
