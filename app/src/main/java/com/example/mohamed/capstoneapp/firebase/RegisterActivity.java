package com.example.mohamed.capstoneapp.firebase;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.capstoneapp.MainActivity;
import com.example.mohamed.capstoneapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    TextInputLayout name, password, email;
    Button createAccount;
    FirebaseAuth firebaseAuth;
    Toolbar toolbar;
    ProgressDialog progressDialog;
    NetworkInfo networkInfo;
    ConnectivityManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = (TextInputLayout) findViewById(R.id.name);
        password = (TextInputLayout) findViewById(R.id.password);
        email = (TextInputLayout) findViewById(R.id.email);
        createAccount = (Button) findViewById(R.id.createAccount);

        firebaseAuth = FirebaseAuth.getInstance();

        manager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo=manager.getActiveNetworkInfo();


        toolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register Screen");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);


        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = name.getEditText().getText().toString();
                String email1 = email.getEditText().getText().toString();
                String password1 = password.getEditText().getText().toString();

                if (!TextUtils.isEmpty(name1) || !TextUtils.isEmpty(email1) || !TextUtils.isEmpty(password1)) {
                    progressDialog.setTitle("Create Your Account");
                    progressDialog.setMessage("Please Wait while Creating Process Finished");
                    progressDialog.show();

                    if(networkInfo!=null&&networkInfo.isConnected()) {
                        register(name1, email1, password1);
                    }else{

                            Toast.makeText(RegisterActivity.this,"make sure network is connected",Toast.LENGTH_LONG).show();

                    }
                }



            }
        });
    }

    private void register(final String name, String email, String password) {
        // Log.v("RegisterActivity","tttttttttt"+task.getResult().toString());
        firebaseAuth.createUserWithEmailAndPassword(email, password)

                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser userUid = firebaseAuth.getCurrentUser();
                            String uid = userUid.getUid();

                            progressDialog.dismiss();
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                            finish();
                        } else {
                            progressDialog.hide();
                            Toast.makeText(getApplicationContext(), "there are error", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        // task.getResult();


    }
}





