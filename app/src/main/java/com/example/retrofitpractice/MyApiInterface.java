package com.example.retrofitpractice;

import com.example.retrofitpractice.modelandentity.Example;
import com.example.retrofitpractice.modelandentity.WebSiteResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MyApiInterface {
        // Request method and URL specified in the annotation

        @GET("getallmanufacturers")
        Call<Example> getData(@Query("format") String format);

    /*    @GET("group/{id}/users")
        Call<List<User>> groupList(@Path("id") int groupId, @Query("sort") String sort);

        @POST("users/new")
        Call<User> createUser(@Body User user);

     */
       // @Headers({"Content-type:application/json"})
        @POST("https://my-json-server.typicode.com/ajith00001/fakeServer/posts")
        Call<List<WebSiteResponse>> postData();
}
