package com.example.retrofitpractice.datarepositories;

import androidx.lifecycle.MutableLiveData;

import com.example.retrofitpractice.ApiServiceProviderJson;
import com.example.retrofitpractice.modelandentity.EntityResult;
import com.example.retrofitpractice.modelandentity.Example;
import com.example.retrofitpractice.MainActivity;
import com.example.retrofitpractice.MyApiInterface;
import com.example.retrofitpractice.modelandentity.WebSiteResponse;

import java.util.ArrayList;
import java.util.List;

public class DataRepository {

    private MyApiInterface apiService = ApiServiceProviderJson.getRetrofit().create(MyApiInterface.class);
    private MutableLiveData<Boolean> progressBarStatus;
    private MutableLiveData<String> errorMessage;
    private APIRepository myAPIRepo;
    private DBRepository myDBRepo;
    public DataRepository(MutableLiveData<Boolean> progressBarStatus, MutableLiveData<String> errorMessage, MainActivity mainActivity, MutableLiveData<ArrayList<EntityResult>> arrayListMutableLiveData) {
        this.progressBarStatus = progressBarStatus;
        this.errorMessage = errorMessage;
        myAPIRepo = new APIRepository(this.progressBarStatus, this.errorMessage);
        myDBRepo = new DBRepository(mainActivity,arrayListMutableLiveData);
    }

    // Calls for API data
    public void fetchAPIData(MutableLiveData<Example> exampleMutableLiveData) {
        myAPIRepo.fetchData(exampleMutableLiveData);
       // myDBRepo.insertData(new Result(temp.country,"Some common Name",temp.mfrId,temp.mfrName,new ArrayList<>(temp.vehicleCount)));
    }

    public void postAPIData(EntityResult.PostClass postClass, MutableLiveData<WebSiteResponse> webSiteResponseMutableLiveData) {

        myAPIRepo.postData(postClass, webSiteResponseMutableLiveData);
    }

    // calls for SQLite Data
    public void getAllUserData()
    {
        myDBRepo.getAllData();
    }
    public void updateResult(EntityResult entityResult)
    {
        myDBRepo.insertData(entityResult);
    }
    public void deleteUserData() {
        myDBRepo.deleteALl();
    }

    public void insertAll(List<EntityResult> entityResults) {
        myDBRepo.insertAll(entityResults);
    }
}
