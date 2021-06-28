package com.example.retrofitpractice.viewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.retrofitpractice.SignInActivity;
import com.example.retrofitpractice.datarepositories.SignInDataRepository;
import com.example.retrofitpractice.modelandentity.Users;

import java.util.List;

public class SignInViewModel extends ViewModel {

    private SignInDataRepository signInDataRepository;
    private MutableLiveData<List<Users>> listMutableLiveData =  new MutableLiveData<>();;
    private MutableLiveData<Boolean> isUserAdded= new MutableLiveData<>();;

    public void getContext(SignInActivity signInActivity) {
        signInDataRepository = new SignInDataRepository(signInActivity, listMutableLiveData, isUserAdded);
    }

    public LiveData<List<Users>> getNewUserRef() {
        return this.listMutableLiveData;
    }

    public LiveData<Boolean> getIsUserAddedRef() {
        return this.isUserAdded;
    }

    public void addUser(String userName, String userEmail, String userPassword) {
        signInDataRepository.addUser(new Users(userEmail, userName, userPassword));

    }

    public void checkUser(String userEmail, String userPassword) {
        signInDataRepository.getUser(userEmail, userPassword);

    }
}
