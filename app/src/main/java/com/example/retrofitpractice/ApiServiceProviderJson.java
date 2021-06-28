package com.example.retrofitpractice;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceProviderJson {

    public static final String BASE_URL = "https://vpic.nhtsa.dot.gov/api/vehicles/";
    private static Retrofit retrofit = null;

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    static {
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    private static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(logging)
            .build();

    public static Retrofit getRetrofit() {

        if (retrofit == null) {
            Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
            retrofitBuilder.baseUrl(BASE_URL);
            retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
            retrofitBuilder.client(client);
            retrofit = retrofitBuilder.build();
        }
        return retrofit;
    }
}
