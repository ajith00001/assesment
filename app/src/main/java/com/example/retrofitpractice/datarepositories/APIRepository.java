package com.example.retrofitpractice.datarepositories;

import androidx.lifecycle.MutableLiveData;

import com.example.retrofitpractice.ApiServiceProviderJson;
import com.example.retrofitpractice.modelandentity.EntityResult;
import com.example.retrofitpractice.modelandentity.Example;
import com.example.retrofitpractice.MyApiInterface;
import com.example.retrofitpractice.modelandentity.WebSiteResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIRepository {
    private MyApiInterface apiService = ApiServiceProviderJson.getRetrofit().create(MyApiInterface.class);
    private MutableLiveData<Boolean> progressBarStatus;
    private MutableLiveData<String> errorMessage;

    public APIRepository(MutableLiveData<Boolean> progressBarStatus, MutableLiveData<String> errorMessage) {
        this.progressBarStatus = progressBarStatus;
        this.errorMessage = errorMessage;
    }

    public void fetchData(MutableLiveData<Example> exampleMutableLiveData) {
        Call<Example> call = apiService.getData("json");
        progressBarStatus.setValue(true);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                int statusCode = response.code();
                exampleMutableLiveData.setValue(response.body());
                progressBarStatus.setValue(false);
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                errorMessage.setValue(t.getMessage());
                errorMessage.setValue("");
                progressBarStatus.setValue(false);
            }
        });
    }

    public void postData(EntityResult.PostClass postClass, MutableLiveData<WebSiteResponse> webSiteResponseMutableLiveData) {

        Call<List<WebSiteResponse>> call = apiService.postData();
        progressBarStatus.setValue(true);
        call.enqueue(new Callback<List<WebSiteResponse>>() {
            @Override
            public void onResponse(Call<List<WebSiteResponse>> call, Response<List<WebSiteResponse>> response) {
                webSiteResponseMutableLiveData.setValue( (response.body() == null) ? new WebSiteResponse():response.body().get(0));
                progressBarStatus.setValue(false);
            }

            @Override
            public void onFailure(Call<List<WebSiteResponse>> call, Throwable t) {
                errorMessage.setValue(t.getMessage());
                errorMessage.setValue("");
                progressBarStatus.setValue(false);
            }
        });
    }
}
