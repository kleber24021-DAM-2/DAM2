package org.quevedo.client.dao.di.retrofit;

import okhttp3.Credentials;
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

        if (cacheAuth.getJwt() == null) {
            request = original.newBuilder()
                    .header(StringConst.AUTHORIZATION, Credentials.basic(cacheAuth.getUser(), cacheAuth.getPass())).build();
        } else {
            request = original.newBuilder()
                    .header(StringConst.AUTHORIZATION, StringConst.BEARER + cacheAuth.getJwt()).build();
        }

        Response response = chain.proceed(request);

        if (response.header(StringConst.AUTHORIZATION) != null) {
            cacheAuth.setJwt(response.header(StringConst.AUTHORIZATION));
        }
        if (response.code() == 418) {
            response.close();
            request = original.newBuilder()
                    .header(StringConst.AUTHORIZATION, Credentials.basic(cacheAuth.getUser(), cacheAuth.getPass())).build();
            response = chain.proceed(request);
            if (response.header(StringConst.AUTHORIZATION) != null) {
                cacheAuth.setJwt(response.header(StringConst.AUTHORIZATION));
            }
        }
        return response;
    }
}

