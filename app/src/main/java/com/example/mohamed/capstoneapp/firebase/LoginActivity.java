package com.example.mohamed.capstoneapp.firebase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mohamed.capstoneapp.MainActivity;
import com.example.mohamed.capstoneapp.R;
import com.example.mohamed.capstoneapp.StartActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextInputLayout email,password;
    Button login;
    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;
    NetworkInfo networkInfo;
    ConnectivityManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar=(Toolbar) findViewById(R.id.login_toolbar);
        email=(TextInputLayout) findViewById(R.id.email);
        password=(TextInputLayout) findViewById(R.id.password);
        login=(Button) findViewById(R.id.login_button);
        progressDialog=new ProgressDialog(this);

        manager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo=manager.getActiveNetworkInfo();

        firebaseAuth=FirebaseAuth.getInstance();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login Screen");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String email1 = email.getEditText().getText().toString();
                    String password1 = password.getEditText().getText().toString();
                    if (!TextUtils.isEmpty(email1) || !TextUtils.isEmpty(password1)) {
                        progressDialog.setTitle("Signin To Your Account");
                        progressDialog.setMessage("Please Wait while Signing Process Finished");
                        progressDialog.show();
                        if(networkInfo!=null&&networkInfo.isConnected()) {
                        loginAccount(email1, password1);
                    }
                    else{
                        Toast.makeText(LoginActivity.this,"make sure network is connected",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });


    }

    private void loginAccount(String email1, String password1) {
        firebaseAuth.signInWithEmailAndPassword(email1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }else {
                    progressDialog.hide();
                    Toast.makeText(LoginActivity.this,"there is an error found try again",Toast.LENGTH_LONG).show();

                }
            }
        });

    }
}
