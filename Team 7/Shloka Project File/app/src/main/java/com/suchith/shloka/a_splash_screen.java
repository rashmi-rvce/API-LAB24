package com.suchith.shloka;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class a_splash_screen extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_splash_screen);

        mAuth = FirebaseAuth.getInstance();



        // SPLASHSCREEN CODE START
        new Handler().postDelayed(() -> {
            if (mAuth.getCurrentUser() != null) {
                startActivity(new Intent(a_splash_screen.this, f_home_page.class));
            } else {
                startActivity(new Intent(a_splash_screen.this, b_welcome_screen.class));
            }
            finish();
        }, 1500);
        // SPLASHSCREEN CODE END
    }

}