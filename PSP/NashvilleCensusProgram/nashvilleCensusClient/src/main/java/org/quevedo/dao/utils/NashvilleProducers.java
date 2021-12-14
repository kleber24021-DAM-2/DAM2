package org.quevedo.dao.utils;

import com.google.gson.*;
import org.quevedo.config.ConfigurationClient;
import org.quevedo.dao.retrofit.NashvilleClientPersonas;
import org.quevedo.dao.retrofit.NashvilleClienteDefunciones;
import org.quevedo.dao.retrofit.NashvilleClienteMatrimonios;
import org.quevedo.dao.retrofit.NashvilleClienteNacimientos;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.inject.Singleton;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class NashvilleProducers {

    @Produces
    @Singleton
    public OkHttpClient getOkHttp(){
        return new OkHttpClient.Builder()
                .protocols(List.of(Protocol.HTTP_1_1))
                .readTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .callTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .connectTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .build();
    }

    @Produces
    @Singleton
    @Named(StringConstants.PATH_BASE)
    public String getPathBase(ConfigurationClient configurationClient){
        return configurationClient.getPathBase();
    }

    @Produces
    @Singleton
    public Gson gsonParser(){
        return new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (jsonElement, type, jsonDeserializationContext) ->
                        LocalDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>)
                        (localDate, type, jsonSerializationContext) -> new JsonPrimitive(localDate.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (jsonElement, type, jsonDeserializationContext) ->
                        LocalDate.parse(jsonElement.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>)
                        (localDate, type, jsonSerializationContext) -> new JsonPrimitive(localDate.toString()))
                .create();
    }

    @Produces
    @Singleton
    public Retrofit createRetrofit(OkHttpClient httpClient, @Named(StringConstants.PATH_BASE) String pathBase, Gson gson){
        return new Retrofit.Builder()
                .baseUrl(pathBase)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build();
    }

    @Produces
    public NashvilleClientPersonas createApiPersonas(Retrofit retrofit){
        return retrofit.create(NashvilleClientPersonas.class);
    }

    @Produces
    public NashvilleClienteNacimientos createApiNacimientos(Retrofit retrofit){
        return retrofit.create(NashvilleClienteNacimientos.class);
    }

    @Produces
    public NashvilleClienteDefunciones createApiDefunciones(Retrofit retrofit){
        return retrofit.create(NashvilleClienteDefunciones.class);
    }

    @Produces
    public NashvilleClienteMatrimonios createApiMatrimonios(Retrofit retrofit){
        return  retrofit.create(NashvilleClienteMatrimonios.class);
    }
}
