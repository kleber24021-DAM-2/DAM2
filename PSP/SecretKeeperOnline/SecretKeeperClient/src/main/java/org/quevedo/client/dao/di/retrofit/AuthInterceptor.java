package org.quevedo.client.dao.di.retrofit;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.quevedo.client.dao.utils.CacheAuth;
import org.quevedo.client.dao.utils.StringConst;

import javax.inject.Inject;
import java.io.IOException;

public class AuthInterceptor implements Interceptor {
    private final CacheAuth cacheAuth;

    @Inject
    AuthInterceptor(CacheAuth cacheAuth) {
        this.cacheAuth = cacheAuth;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request;

        //Enviamos la request
        if (cacheAuth.getJwt() == null) {
            //Si el cacheo del jwt es null, enviamos el certificado
            request = original.newBuilder()
                    .header(StringConst.AUTHORIZATION, StringConst.CERT + cacheAuth.getPersonalCert()).build();
        } else {
            //Si existe un jwt guardado, lo enviamos
            request = original.newBuilder()
                    .header(StringConst.AUTHORIZATION, StringConst.BEARER + cacheAuth.getJwt()).build();
        }

        Response response = chain.proceed(request);

        //Recibimos la respuesta
        if (response.header(StringConst.AUTHORIZATION) != null) {
            //Si nos envian un jwt, lo guardamos para la próxima llamada
            cacheAuth.setJwt(response.header(StringConst.AUTHORIZATION));
        }
        //Si el código de respuesta es el 418 o el 401, el token ha expirado, y por tanto volvemos a enviar nuestro cert
        if (response.code() == 418 || response.code() == 401) {
            response.close();
            request = original.newBuilder()
                    .header(StringConst.AUTHORIZATION, StringConst.CERT + cacheAuth.getPersonalCert()).build();
            response = chain.proceed(request);
            if (response.header(StringConst.AUTHORIZATION) != null) {
                cacheAuth.setJwt(response.header(StringConst.AUTHORIZATION));
            }
        }
        return response;
    }
}

