package com.suchith.shloka;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class v_settings extends AppCompatActivity {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch a_Switch;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.v_settings);







        Button Button2 = findViewById(R.id.back_btn_Settings);
        Button2.setOnClickListener(v -> {
            Intent intent1 = new Intent(v_settings.this, f_home_page.class);
            startActivity(intent1);
        });






        // Logout Button Start
        mAuth = FirebaseAuth.getInstance();
        Button btn_Logout = findViewById(R.id.Log_out_btn);
        btn_Logout.setOnClickListener(v -> {
            // Show dialog box asking user to confirm logout
            new AlertDialog.Builder(v_settings.this)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        mAuth.signOut();
                        Intent intent = new Intent(v_settings.this, b_welcome_screen.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(v_settings.this, "Logout Successful !", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });


    }






}