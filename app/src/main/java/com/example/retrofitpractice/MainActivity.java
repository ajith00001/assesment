package com.example.retrofitpractice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


import com.example.retrofitpractice.modelandentity.EntityResult;
import com.example.retrofitpractice.modelandentity.Example;
import com.example.retrofitpractice.modelandentity.Result;
import com.example.retrofitpractice.modelandentity.WebSiteResponse;
import com.example.retrofitpractice.viewModels.MyViewModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.MyEditInterface{


    private TextView tvTemporary;
    private MyViewModel myViewModel;
    private ProgressBar dataProgressbar;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    private SharedPreferences sharedPreferences;
    private final int SIGN_IN_REQ_CODE = 1;
    private final int REQUEST_IMAGE_CAPTURE = 11;
    private EntityResult tempEntityResult;
    private int tempImageUpdatePosition ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        myViewModel.initDataRep(this);
        myViewModel.getExampleRef().observe(this, example -> {
            if (example != null)
                setGetExamples(example);
        });
        myViewModel.getWebsiteResponse().observe(this, webSiteResponse -> {
            setPostWebsiteResponse(webSiteResponse);
        });
        myViewModel.getProgressStatusBar().observe(this, bool -> {
            if (bool) {
                dataProgressbar.setVisibility(View.VISIBLE);
            } else {
                dataProgressbar.setVisibility(View.GONE);
            }
        });
        myViewModel.getErrorString().observe(this, error ->{
            if(error.length() > 0)
            Toast.makeText(this,"Error :"+error,Toast.LENGTH_SHORT).show();
        });
        myViewModel.getListDataRef().observe(this,list ->{
            myRecyclerViewAdapter.setResults(list);
            dataProgressbar.setVisibility(View.GONE);
        });

        setApp();

    }

    public void setApp(){
        sharedPreferences = getSharedPreferences(getString(R.string.login_cred),MODE_PRIVATE);
        boolean userLoginStatus = sharedPreferences.getBoolean(getString(R.string.login_status),false);
        if(userLoginStatus == false)
        {
            askSignIn();
        }
        else {
            tvTemporary.setText("Hi "+sharedPreferences.getString(getString(R.string.login_user_name),"New User")+" !");
            dataProgressbar.setVisibility(View.VISIBLE);
            myViewModel.getAllUserData();
        }
    }

    public void initViews() {
        tvTemporary = (TextView) findViewById(R.id.tv_temporary);
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        dataProgressbar = findViewById(R.id.data_progress_bar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_results);
        myRecyclerViewAdapter = new MyRecyclerViewAdapter();
        myRecyclerViewAdapter.setMyEditInterface(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myRecyclerViewAdapter);
    }

    public void fetchData(View view) {
        tvTemporary.setText("Fetching Data");
        myViewModel.fetchAPIData();
    }

    public void postData(View view) {
        tvTemporary.setText("Posting Data");
        myViewModel.postAPIData("I am test");
    }


    public void setGetExamples(Example example) {
        tvTemporary.setText("Received Data");
        ArrayList<EntityResult> resultArrayList = new ArrayList<>();
        for(Result temp : example.getResults())
        {
            resultArrayList.add(new EntityResult(temp.getMfrID(),temp.getCountry(),temp.getMfrName(),temp.getVehicleTypes().size()));
        }

      //  myRecyclerViewAdapter.setResults((ArrayList<EntityResult>) resultArrayList);
        myViewModel.insertAll(resultArrayList);
    }


    public void setGetFailure(String error) {
        tvTemporary.setText("Unable to Fetch Data");
        Toast.makeText(this, "Error : " + error, Toast.LENGTH_SHORT).show();
    }


    public void setPostWebsiteResponse(WebSiteResponse websiteResponse) {
        tvTemporary.setText(websiteResponse.getInput() + " > " + websiteResponse.getOutput());
    }


    public void setPostFailure(String error) {
        tvTemporary.setText("Error in Posting");
        Toast.makeText(this, "Error : " + error, Toast.LENGTH_SHORT).show();
    }


    public void logout(View view){

        sharedPreferences = getSharedPreferences(getString(R.string.login_cred),MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(getString(R.string.login_status),false);
        editor.putString(getString(R.string.login_user_name),"");

        editor.commit();

        myViewModel.deleteUserData();
        askSignIn();

    }

    private void askSignIn()
    {
        Intent intent = new Intent(this,SignInActivity.class);
        startActivityForResult(intent,SIGN_IN_REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN_REQ_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                sharedPreferences = getSharedPreferences(getString(R.string.login_cred),MODE_PRIVATE);
                boolean userLoginStatus = sharedPreferences.getBoolean(getString(R.string.login_status),false);
                tvTemporary.setText("Hi "+sharedPreferences.getString(getString(R.string.login_user_name),"New User")+" !");

            }
            else if(resultCode == RESULT_CANCELED)
            {
                makeDialog("You must SIGN IN to use this App !");
               // Toast.makeText(this,"You must SIGN IN to use this App !",Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 60, bos);
            byte[] img = bos.toByteArray();
            updateView(img);
        }
    }
    public void makeDialog(String message){

        AlertDialog.Builder builder = new AlertDialog.Builder (this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate (R.layout.alert_dailog,  null);

        builder.setCancelable(false);

        builder.setView(dialogView);

        TextView tvMessage, tvClickableOK;
        tvMessage = (TextView) dialogView.findViewById(R.id.tv_alert_dailog_message);
        tvClickableOK = (TextView) dialogView.findViewById(R.id.tv_alert_dailog_clickable) ;

        AlertDialog alertDialog = builder.create();
        tvMessage.setText(message);

        tvClickableOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                finish();
            }
        });
        alertDialog.show();

    }

    @Override
    public void editAt(EntityResult entityResult,int position) {
        tempEntityResult = entityResult;
        dispatchTakePictureIntent();
        tempImageUpdatePosition = position;
       // myViewModel.updateResult(entityResult);
    }
    private void updateView(byte[] bytes){
        tempEntityResult.setImage(bytes);
        tempEntityResult.setImageStatus(true);
        myViewModel.updateResult(tempEntityResult);
        myRecyclerViewAdapter.setItem(tempEntityResult,tempImageUpdatePosition);
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }


}