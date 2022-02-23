package org.quevedo.client.dao.di.retrofit;

import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import org.quevedo.client.config.Configuration;
import org.quevedo.client.dao.di.retrofit.interfaces.UsuariosApi;
import org.quevedo.client.dao.utils.StringConst;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

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

public class RetrofitModule {
    @Produces
    @Singleton
    public OkHttpClient getOkHttp() {
        return new OkHttpClient.Builder()
                .protocols(List.of(Protocol.HTTP_1_1))
                .readTimeout(Duration.of(3, ChronoUnit.MINUTES))
                .callTimeout(Duration.of(3, ChronoUnit.MINUTES))
                .connectTimeout(Duration.of(3, ChronoUnit.MINUTES))
//                .addInterceptor(authInterceptor)
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
    @Named(StringConst.CONFIG)
    public String getPathBase(Configuration configurationClient) {
        configurationClient.loadConfig();
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
    public Retrofit createRetrofit(OkHttpClient httpClient, @Named(StringConst.CONFIG) String pathBase, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(pathBase)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(httpClient)
                .build();
    }

    @Produces
    @Singleton
    public UsuariosApi createUsuariosApi(Retrofit retrofit){
        return retrofit.create(UsuariosApi.class);
    }
}
