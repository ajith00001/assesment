package com.example.retrofitpractice.datarepositories;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.retrofitpractice.DBHandler;
import com.example.retrofitpractice.modelandentity.EntityResult;
import com.example.retrofitpractice.EntityResultDAO;
import com.example.retrofitpractice.MainActivity;
import com.example.retrofitpractice.modelandentity.Result;

import java.util.ArrayList;
import java.util.List;

public class DBRepository {
    private final DBHandler dbHandler;
    private final EntityResultDAO entityResultDAO;
    private MutableLiveData<ArrayList<EntityResult>> listMutableLiveData;

    public DBRepository(MainActivity mainActivity, MutableLiveData<ArrayList<EntityResult>> arrayListMutableLiveData) {
        dbHandler = Room.databaseBuilder(mainActivity, DBHandler.class, "app_data").build();
        entityResultDAO = dbHandler.entityResultDAO();
        this.listMutableLiveData = arrayListMutableLiveData;
    }

    public void getAllData()
    {
        new AsyncTask<Void,Void,Void>(){
            List<EntityResult> entityResults;
            @Override
            protected Void doInBackground(Void... voids) {
                entityResults = entityResultDAO.getAll();
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                listMutableLiveData.setValue((ArrayList<EntityResult>) entityResults);
            }
        }.execute();
    }

    public void insertData(EntityResult entityResult)
    {
        new AsyncTask<Void,Void,Void>(){
            List<EntityResult> entityResultList;
            @Override
            protected Void doInBackground(Void... voids) {
                entityResultDAO.insert(entityResult);
             /*   entityResultDAO.insert(new EntityResult(1,"Austraila","Deccan Motors",10));
                entityResultDAO.insert(new EntityResult(4,"US","Audi Motors",10));
                entityResultDAO.insert(new EntityResult(2,"Japan","Japanese Motors",1));
                entityResultDAO.insert(new EntityResult(3,"UK","Bentley Motors",8));
              */   entityResultList = entityResultDAO.getAll();
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                listMutableLiveData.setValue((ArrayList<EntityResult>) entityResultList);
            }
        }.execute();
    }

    public void insertAll(List<EntityResult> entityResultArrayList){

        new AsyncTask<Void,Void,Void>() {

            List<EntityResult> entityResults;
            @Override
            protected Void doInBackground(Void... voids) {
             /*   for(EntityResult temp :entityResultArrayList){
                    entityResultDAO.insert(temp);
                }

              */
                entityResultDAO.insertAll(entityResultArrayList);
                entityResults = entityResultDAO.getAll();
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                listMutableLiveData.setValue((ArrayList<EntityResult>) entityResults);
            }
        }.execute();


    }

    public void deleteALl()
    {
        new AsyncTask<Void,Void,Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                entityResultDAO.deleteAll();
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                listMutableLiveData.setValue(new ArrayList<>());
            }
        }.execute();

    }

}
