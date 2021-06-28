package com.example.retrofitpractice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofitpractice.viewModels.SignInViewModel;

public class SignInActivity extends AppCompatActivity {

    private EditText evUserName, evUserEmail, evUserPassword;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String passwordPattern = "[^\\s]";
    private SignInViewModel signInViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        evUserName = (EditText) findViewById(R.id.ev_user_name);
        evUserEmail = (EditText) findViewById(R.id.ev_user_email);
        evUserPassword = (EditText) findViewById(R.id.ev_user_password);
        signInViewModel = new ViewModelProvider(this).get(SignInViewModel.class);
        signInViewModel.getContext(this);
        signInViewModel.getIsUserAddedRef().observe(this, isAdded ->{
            if(isAdded) {
                makeDialog("User Added");
            }
            else {

                makeDialog("Email already taken !");
                // Show that unable to create user
             //   Toast.makeText(this,"email already taken !",Toast.LENGTH_SHORT).show();
            }
        });
        signInViewModel.getNewUserRef().observe(this, user ->{
            if(user.size() >0)
            {
                setSharedPreferences(user.get(0).getUserName());
                setResult(RESULT_OK, new Intent());
                finish();
            }
            else {

                // SHow that you are an invalid user !
                makeDialog("Invalid email or password");
              //  Toast.makeText(this,"Invalid email or password",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void signIn(View view){
        boolean canProceed = true;
        String userEmail,userPassword;

        userEmail = evUserEmail.getText().toString();
        userPassword = evUserPassword.getText().toString();

        canProceed = checkEmailAndPassword(userEmail,userPassword);
        if(canProceed) {

           signInViewModel.checkUser(userEmail,userPassword);
        }
    }
    public void signUp(View view){
        boolean canProceed = true;
        String userEmail,userPassword,userName;

        userEmail = evUserEmail.getText().toString();
        userPassword = evUserPassword.getText().toString();
        userName =evUserName.getText().toString();

        canProceed = checkEmailAndPassword(userEmail,userPassword);
        if(userName.length() == 0)
        {
            evUserName.setError("Please enter name");
            canProceed = false;
        }
        if(canProceed) {
            // add user
            //do sign up
            signInViewModel.addUser(userName,userEmail,userPassword);

        }
    }
    private boolean checkEmailAndPassword(String userEmail,String userPassword)
    {
        boolean canProceed = true;
        if(userEmail.length() == 0)
        {
            evUserEmail.setError("Please enter email");
            canProceed = false;
        }
        else if(!userEmail.matches(emailPattern))
        {
            evUserEmail.setError("Please enter valid email");
            canProceed = false;
        }
        if(userPassword.length() == 0)
        {
            evUserPassword.setError("Please enter Password");
            canProceed = false;
        }
        else if(hasWhiteSpace(userPassword))
        {
            evUserPassword.setError("Password can't contain space");
            canProceed = false;
        }
        return canProceed;
    }

    private boolean hasWhiteSpace(String userPassword) {
        for(int i=0;i< userPassword.length();i++)
        {
            if(userPassword.charAt(i) == ' ')
                return true;
        }
        return false;
    }

    private void setSharedPreferences(String userName)
    {
        sharedPreferences = getSharedPreferences(getString(R.string.login_cred),MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean(getString(R.string.login_status),true);
        editor.putString(getString(R.string.login_user_name),userName);
        editor.commit();
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
            }
        });
        alertDialog.show();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED,intent);
        super.onBackPressed();
    }
}