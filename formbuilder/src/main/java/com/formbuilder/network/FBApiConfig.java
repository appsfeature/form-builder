package com.formbuilder.network;

import com.formbuilder.model.FBNetworkModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by Amit on 3/28/2018.
 */

public interface FBApiConfig {

    @GET("{endpoint}")
    Call<FBNetworkModel> getData(@Path("endpoint") String endpoint, @QueryMap Map<String, String> options);

    @POST("{endpoint}")
    Call<FBNetworkModel> postData(@Path("endpoint") String endpoint, @QueryMap Map<String, String> options);

    @GET
    Call<FBNetworkModel> getDataRequest(@Url String url, @QueryMap Map<String, String> options);

    @POST
    Call<FBNetworkModel> postDataRequest(@Url String url, @QueryMap Map<String, String> options);

}
