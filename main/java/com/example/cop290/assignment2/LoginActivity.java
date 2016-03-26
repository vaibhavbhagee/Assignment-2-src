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
        kerbID.setText(sharedpreferences.getString("kerbID",""));
        password.setText(sharedpreferences.getString("password",""));

        if(kerbID.getText().toString() != "" || password.getText().toString() != "")
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

       /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();*/
        Intent intent = new Intent(thisContext, MainActivity.class);
        startActivity(intent);
    }


}
