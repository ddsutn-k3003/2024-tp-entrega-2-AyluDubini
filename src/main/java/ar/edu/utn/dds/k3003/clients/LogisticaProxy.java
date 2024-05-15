package ar.edu.utn.dds.k3003.clients;

import ar.edu.utn.dds.k3003.facades.FachadaHeladeras;
import ar.edu.utn.dds.k3003.facades.FachadaLogistica;
import ar.edu.utn.dds.k3003.facades.FachadaViandas;
import ar.edu.utn.dds.k3003.facades.dtos.RutaDTO;
import ar.edu.utn.dds.k3003.facades.dtos.TrasladoDTO;
import ar.edu.utn.dds.k3003.facades.exceptions.TrasladoNoAsignableException;
import com.fasterxml.jackson.databind.ObjectMapper;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.List;
import java.util.NoSuchElementException;


public class LogisticaProxy implements FachadaLogistica {

    private final String endpoint;
    private final LogisticaRetrofitClient service;
    public LogisticaProxy(ObjectMapper objectMapper) {

        var env = System.getenv();
        this.endpoint = env.getOrDefault("URL_LOGISTICA", "http://localhost:8081/");

        var retrofit =
                new Retrofit.Builder()
                        .baseUrl(this.endpoint)
                        .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                        .build();

        this.service = retrofit.create(LogisticaRetrofitClient.class);
    }


public RutaDTO agregar(RutaDTO var1){return null;}

public TrasladoDTO buscarXId(Long var1) throws NoSuchElementException
    {return null;}

public TrasladoDTO asignarTraslado(TrasladoDTO var1) throws TrasladoNoAsignableException{return null;}

public List<TrasladoDTO> trasladosDeColaborador(Long var1, Integer var2, Integer var3){return null;}

public void setHeladerasProxy(FachadaHeladeras var1){}

public void setViandasProxy(FachadaViandas var1){}

public void trasladoRetirado(Long var1){}

public void trasladoDepositado(Long var1){}
}
