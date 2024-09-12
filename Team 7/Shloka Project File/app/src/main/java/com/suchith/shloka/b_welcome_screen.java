package com.suchith.shloka;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class b_welcome_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_welcome_screen);


        //Buttons Code Start
        Button Button1 =  findViewById(R.id.wcs_btn_create_account);
        Button1.setOnClickListener(v -> {
            Intent intent = new Intent(b_welcome_screen.this, d_signup_page.class);
            startActivity(intent);
        });

        Button Button2 =  findViewById(R.id.wcs_btn_login);
        Button2.setOnClickListener(v -> {
            Intent intent = new Intent(b_welcome_screen.this, c_login_page.class);
            startActivity(intent);
        });
        //Buttons Code End

    }


}