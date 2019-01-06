package com.example.asus.movinguplms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginSignupMenuActivity extends AppCompatActivity {

    private Button BtnLogin, BtnSignUp;
    private EditText EdtEmail, EdtPassword;
    private ProgressDialog loadingBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup_menu);

        mAuth = FirebaseAuth.getInstance();

        BtnLogin = (Button) findViewById(R.id.btnLogin);
        BtnSignUp = (Button) findViewById(R.id.btnSignUp);
        EdtEmail = (EditText) findViewById(R.id.textEmail);
        EdtPassword = (EditText) findViewById(R.id.textPassword);
        loadingBar = new ProgressDialog(this);

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllowingUserToLogin();
            }
        });
        BtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToRegisterActivity();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
        {
            SendUserToMenuActivity();
        }
    }

    private void AllowingUserToLogin() {
        String email = EdtEmail.getText().toString();
        String password = EdtPassword.getText().toString();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please write your email...",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password...",Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Login");
            loadingBar.setMessage("Please wait, while we are allowing you to login to your Account...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                SendUserToMenuActivity();
                                Toast.makeText(LoginSignupMenuActivity.this, "you are Login successfully...",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                            else
                            {
                                String message = task.getException().getMessage();
                                Toast.makeText(LoginSignupMenuActivity.this, "Error occured: " + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }

    private void SendUserToMenuActivity() {
        Intent mainIntent = new Intent(LoginSignupMenuActivity.this, Menu.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    private void SendUserToRegisterActivity() {
        Intent mainIntent = new Intent(LoginSignupMenuActivity.this, RegisterUserActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}
