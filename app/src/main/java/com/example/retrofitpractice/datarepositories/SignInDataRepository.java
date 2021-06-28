package com.example.retrofitpractice.datarepositories;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.retrofitpractice.DBHandler;
import com.example.retrofitpractice.SignInActivity;
import com.example.retrofitpractice.UsersDAO;
import com.example.retrofitpractice.modelandentity.Users;

import java.util.List;

public class SignInDataRepository {

    private DBHandler dbHandler;
    private UsersDAO usersDAO;
    private Context context;
    private MutableLiveData<List<Users>> listMutableLiveData;
    private MutableLiveData<Boolean> isUserAdded;

    public SignInDataRepository(SignInActivity signInActivity, MutableLiveData<List<Users>> listMutableLiveData, MutableLiveData<Boolean> isUserAdded) {
        this.context = signInActivity;
        dbHandler = Room.databaseBuilder(context,DBHandler.class,"app_data")
                //.addMigrations(DBHandler.MIGRATION_1_2)
         .build();
        usersDAO = dbHandler.usersDAO();
        this.listMutableLiveData = listMutableLiveData;
        this.isUserAdded = isUserAdded;
    }

    public void addUser(Users users)
    {
        new AsyncTask<Void,Void,Void>(){
            boolean status;
            @Override
            protected Void doInBackground(Void... voids) {
                try{
                usersDAO.insertUser(users);
                status = true;
                }
                catch (Exception e)
                {
                    status = false;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                isUserAdded.setValue(status);
            }
        }.execute();
    }

    public void getUser(String userEmail,String userPassword){

        List<Users> users;
        new AsyncTask<Void,Void,Void>(){
        List<Users> users;
        @Override
        protected Void doInBackground(Void... voids) {
            users = usersDAO.getUser(userEmail,userPassword);
            return null;
        }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                listMutableLiveData.setValue(users);
            }
        }.execute();

    }
}
