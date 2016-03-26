package com.example.cop290.assignment2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

    public static final String SharedPref = "MahPrefs";
    Context thisContext = this;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText kerbID = (EditText) findViewById(R.id.kerbID);
        final EditText password = (EditText) findViewById(R.id.password);
        sharedpreferences = getSharedPreferences(SharedPref, Context.MODE_PRIVATE);
        kerbID.setText(sharedpreferences.getString("kerbID", ""));
        password.setText(sharedpreferences.getString("password", ""));

        if( !(sharedpreferences.getString("kerbID","") == "") && !(sharedpreferences.getString("password","") == "") )
            login(kerbID,password);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(kerbID,password);

            }
        });
    }

    public void login(EditText kerbID, EditText password)
    {
        String kerberosIDString  = kerbID.getText().toString();
        String passwordString = password.getText().toString();

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("kerbID", kerberosIDString);
        editor.putString("password", passwordString);
        editor.commit();




            //The following event handler listens for response from server and takes appropriate action

            final String ServerURL = "http://cm-system-iitd.herokuapp.com/api/login";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerURL,
                    new Response.Listener<String>() {
                        @Override
                        //On valid response
                        public void onResponse(String response) {

                            Intent intent = new Intent(thisContext, MainActivity.class);
                            System.out.println(response);
                            Bundle b = new Bundle();
                            startActivity(intent);

                        }
                    },
                    //Launched when server return error
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LoginActivity.this, "Server Error. Please check your internet connection.", Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                //Organises data as JSON to be sent to server through POST request
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", ((EditText) findViewById(R.id.kerbID)).getText().toString());
                    params.put("password", ((EditText) findViewById(R.id.password)).getText().toString());
                    return params;
                }

            };
            //Manages the queue of requests
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);






       /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();*/
     /*   Intent intent = new Intent(thisContext, MainActivity.class);
        startActivity(intent);*/
    }


}
