package com.example.cop290.assignment2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {


    public static boolean login_control;
    public static final String SharedPref = "MahPrefs";
    Context thisContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText kerbID = (EditText) findViewById(R.id.kerbID);
        final EditText password = (EditText) findViewById(R.id.password);

        //SharedPreferences sharedpreferences = getSharedPreferences(SharedPref,Context.MODE_PRIVATE);

        SharedPreferences sharedpreferences = getSharedPreferences(SharedPref, Context.MODE_PRIVATE);
        kerbID.setText(sharedpreferences.getString("kerbID", ""));
        password.setText(sharedpreferences.getString("password", ""));

        if( !(sharedpreferences.getString("kerbID","") == "") && !(sharedpreferences.getString("password","") == "") )
            login(kerbID,password);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 login(kerbID,password);

                //Unbypassed
                // By pass login
                /*Intent intent = new Intent(thisContext, MainActivity.class);
                startActivity(intent);*/

            }
        });
    }

    public void login(EditText kerbID, EditText password)
    {
        String kerberosIDString  = kerbID.getText().toString();
        String passwordString = password.getText().toString();

        SharedPreferences sharedpreferences = getSharedPreferences(SharedPref, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("kerbID", kerberosIDString);
        editor.putString("password", passwordString);
        editor.commit();


        LoadData loadDataObject = new LoadData();
        loadDataObject.setContext(thisContext);
        loadDataObject.login_request(kerberosIDString, passwordString);
        timer(1, loadDataObject, 0);
        loadDataObject.flag[0] = false;
        //TODO: Always loggs in

        /*Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);*/

        //TODO: Unsuccesful login, Store token in Shared Preferences


       /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();*/
     /*   Intent intent = new Intent(thisContext, MainActivity.class);
        startActivity(intent);*/
    }






    public boolean timer(final int x, final LoadData l, final int whichflag){

        new CountDownTimer(50, 1000) {
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                if(x==100){
                    Toast.makeText(LoginActivity.this,"Connection Timed Out", Toast.LENGTH_LONG).show();
                }
                else if(l.flag[whichflag]){
                    if(whichflag == 0)
                    {
                        SharedPreferences sharedpreferences = getSharedPreferences(SharedPref, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("token", l.token);
                        editor.commit();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                    }

                } else {
                    timer(x+1,l, whichflag);
                }
            }
        }.start();
        return true;
    }


}
