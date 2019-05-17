package com.example.mohamed.capstoneapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mohamed.capstoneapp.firebase.LoginActivity;
import com.example.mohamed.capstoneapp.firebase.RegisterActivity;

public class StartActivity extends AppCompatActivity {


    private Button mReg;
    private Button mLog;
    ConnectivityManager manager;
    NetworkInfo networkInfo;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mReg=(Button) findViewById(R.id.reg_button);
        mLog=(Button) findViewById(R.id.old_account_button);



         manager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
         networkInfo=manager.getActiveNetworkInfo();



        mReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent reg = new Intent(StartActivity.this, RegisterActivity.class);
                    startActivity(reg);
                }


        });
        mLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent reg=new Intent(StartActivity.this,LoginActivity.class);
                startActivity(reg);


            }
        });

    }
}
