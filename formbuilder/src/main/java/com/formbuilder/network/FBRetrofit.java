package com.formbuilder.network;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.formbuilder.FormBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Amit on 3/28/2018.
 */

public class FBRetrofit {

    private static final String defaultBaseUrl = "http://appsfeature.com/";

    public static Retrofit getClient(String baseUrl) {
        return getClient(baseUrl,"", null);
    }

    @Nullable
    public static Retrofit getClient(String baseUrl, String securityCode, String securityCodeEnc) {
        if(baseUrl == null){
            baseUrl = defaultBaseUrl;
        }
        Retrofit retrofit = null;
        try {
            if(!baseUrl.endsWith("/")){
                baseUrl += "/";
            }
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(getHttpClient(securityCode, securityCodeEnc, FormBuilder.getInstance().isDebugModeEnabled()).build())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retrofit;
    }

    private static OkHttpClient.Builder getHttpClient(final String securityCode, final String securityCodeEnc, boolean isDebug) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        Request request;
                        if (!TextUtils.isEmpty(securityCodeEnc)) {
                            request = original.newBuilder()
                                    .header("Authorization", securityCode)
                                    .header("Authorization-Enc", securityCodeEnc)
                                    .method(original.method(), original.body())
                                    .build();
                        } else {
                            request = original.newBuilder()
                                    .header("Authorization", securityCode)
                                    .method(original.method(), original.body())
                                    .build();
                        }

                        return chain.proceed(request);
                    }
                });
        if (isDebug) {
            builder.addInterceptor(loggingInterceptor);
        }
        return builder;
    }

    private static final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

}
