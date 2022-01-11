package org.quevedo.client.dao.retrofit;

import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import org.quevedo.client.config.ConfigurationClient;
import org.quevedo.client.dao.retrofit.interfaces.EquipoApi;
import org.quevedo.client.dao.retrofit.interfaces.JornadaApi;
import org.quevedo.client.dao.retrofit.interfaces.PartidosApi;
import org.quevedo.client.dao.retrofit.interfaces.UsersApi;
import org.quevedo.client.dao.utils.OwnCookies;
import org.quevedo.client.dao.utils.StringConstants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.inject.Singleton;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class RetrofitProducers {

    @Produces
    @Singleton
    public OkHttpClient getOkHttp() {
        return new OkHttpClient.Builder()
                .protocols(List.of(Protocol.HTTP_1_1))
                .readTimeout(Duration.of(3, ChronoUnit.MINUTES))
                .callTimeout(Duration.of(3, ChronoUnit.MINUTES))
                .connectTimeout(Duration.of(3, ChronoUnit.MINUTES))
                .cookieJar(new OwnCookies(getCookieManager()))
                .build();
    }

    @Produces
    @Singleton
    public CookieManager getCookieManager() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        return cookieManager;
    }

    @Produces
    @Singleton
    @Named(StringConstants.CONFIG)
    public String getPathBase(ConfigurationClient configurationClient) {
        return configurationClient.getPathBase();
    }


    @Produces
    @Singleton
    public Gson gsonParser() {
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
    public Retrofit createRetrofit(OkHttpClient httpClient, @Named(StringConstants.CONFIG) String pathBase, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(pathBase)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build();
    }

    @Produces
    @Singleton
    public EquipoApi equipoApiProducer(Retrofit retrofit) {
        return retrofit.create(EquipoApi.class);
    }

    @Produces
    @Singleton
    public JornadaApi jornadaApiProducer(Retrofit retrofit) {
        return retrofit.create(JornadaApi.class);
    }

    @Produces
    @Singleton
    public PartidosApi partidosApiProducer(Retrofit retrofit) {
        return retrofit.create(PartidosApi.class);
    }

    @Produces
    @Singleton
    public UsersApi usersApiProducer(Retrofit retrofit) {
        return retrofit.create(UsersApi.class);
    }
}
