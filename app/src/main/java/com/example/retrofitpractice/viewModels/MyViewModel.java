package com.example.retrofitpractice.viewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.retrofitpractice.datarepositories.DataRepository;
import com.example.retrofitpractice.modelandentity.EntityResult;
import com.example.retrofitpractice.modelandentity.Example;
import com.example.retrofitpractice.MainActivity;
import com.example.retrofitpractice.modelandentity.WebSiteResponse;

import java.util.ArrayList;
import java.util.List;

public class MyViewModel extends ViewModel {


    private MutableLiveData<Example> exampleMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<WebSiteResponse> webSiteResponseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> progressBarStatus = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<ArrayList<EntityResult>> arrayListMutableLiveData = new MutableLiveData<>();
    private DataRepository dataRepository;
    private Context context;

    public void initDataRep(MainActivity mainActivity) {

        dataRepository = new DataRepository(progressBarStatus, errorMessage, mainActivity, arrayListMutableLiveData);
    }

    // Getters
    public LiveData<Example> getExampleRef() {
        return this.exampleMutableLiveData;
    }

    public LiveData<ArrayList<EntityResult>> getListDataRef() {
        return this.arrayListMutableLiveData;
    }

    public LiveData<WebSiteResponse> getWebsiteResponse() {
        return this.webSiteResponseMutableLiveData;
    }

    public LiveData<Boolean> getProgressStatusBar() {
        return this.progressBarStatus;
    }

    public LiveData<String> getErrorString() {
        return this.errorMessage;
    }


    // make actions
    public void fetchAPIData() {
        dataRepository.fetchAPIData(exampleMutableLiveData);
    }

    public void postAPIData(String message) {
        EntityResult.PostClass postClass = new EntityResult.PostClass(message);
        dataRepository.postAPIData(postClass, webSiteResponseMutableLiveData);
    }

    public void getAllUserData(){

        dataRepository.getAllUserData();
    }

    public void updateResult(EntityResult entityResult)
    {
        dataRepository.updateResult(entityResult);
    }

    public void insertAll(List<EntityResult> entityResults)
    {
        dataRepository.insertAll(entityResults);
    }
    public void deleteUserData() {
        dataRepository.deleteUserData();
    }
}
