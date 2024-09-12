package com.suchith.shloka;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class c_login_page extends AppCompatActivity {
    TextView txtSignUp, txtForgotPass;
    EditText edtEmail, edtPassword;
    ProgressBar progressBar;
    Button btnSignIn;
    String txtEmail, txtPassword;
    final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_login_page);



        txtForgotPass = findViewById(R.id.txtForgetPassword);
        txtSignUp = findViewById(R.id.txtSignUp);
        edtEmail = findViewById(R.id.edtSignInEmail);
        edtPassword = findViewById(R.id.edtSignInPassword);
        progressBar = findViewById(R.id.signInProgressBar);
        btnSignIn = findViewById(R.id.loin_page_btn_login_In);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        txtSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(c_login_page.this, d_signup_page.class);
            startActivity(intent);
            finish();
        });

        txtForgotPass.setOnClickListener(view -> {
            Intent intent = new Intent(c_login_page.this, e_forgot_password.class);
            startActivity(intent);
            finish();
        });

        btnSignIn.setOnClickListener(view -> {
            txtEmail = edtEmail.getText().toString().trim();
            txtPassword = edtPassword.getText().toString().trim();

            if (!TextUtils.isEmpty(txtEmail)) {
                if (txtEmail.matches(emailPattern)) {
                    if (!TextUtils.isEmpty(txtPassword)) {
                        SignInUser();
                    } else {
                        edtPassword.setError("Password Field can't be empty");
                    }
                } else {
                    edtEmail.setError("Enter a valid Email Address");
                }
            } else {
                edtEmail.setError("Email Field can't be empty");
            }
        });
    }

    private void SignInUser() {
        progressBar.setVisibility(View.VISIBLE);
        btnSignIn.setVisibility(View.INVISIBLE);

        // Check network connectivity
        if (!isNetworkAvailable()) {
            Toast.makeText(c_login_page.this, "No internet connection. Please check your network settings.", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
            btnSignIn.setVisibility(View.VISIBLE);
            return;
        }

        try {
            mAuth.signInWithEmailAndPassword(txtEmail, txtPassword).addOnSuccessListener(authResult -> {
                Toast.makeText(c_login_page.this, "Login Successful !", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(c_login_page.this, f_home_page.class);
                startActivity(intent);
                finish();
            }).addOnFailureListener(e -> {
                String errorMessage;
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    errorMessage = "Invalid email or password. Please try again.";
                } else if (e instanceof FirebaseAuthInvalidUserException) {
                    errorMessage = "No account found with this email. Please sign up.";
                } else if (e instanceof FirebaseNetworkException) {
                    errorMessage = "Network error. Please check your internet connection and try again.";
                } else {
                    errorMessage = "Login failed: " + e.getMessage();
                }
                Toast.makeText(c_login_page.this, errorMessage, Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
                btnSignIn.setVisibility(View.VISIBLE);
            });
        } catch (Exception e) {
            Toast.makeText(c_login_page.this, "An unexpected error occurred. Please try again.", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
            btnSignIn.setVisibility(View.VISIBLE);
        }
    }

    // Add this method to check network connectivity
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }


}