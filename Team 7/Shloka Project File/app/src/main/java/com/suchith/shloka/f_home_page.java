package com.suchith.shloka;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;

public class f_home_page extends AppCompatActivity {

    private CardView assetManagementCard, spareManagementCard, maintenanceCard,
            analyticsCard, customerSupportCard, settingsCard;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f_home_page);



        // Initialize CardViews
        assetManagementCard = findViewById(R.id.hp_card1);
        spareManagementCard = findViewById(R.id.hp_card2);
        maintenanceCard = findViewById(R.id.hp_card3);
        analyticsCard = findViewById(R.id.hp_card4);
        settingsCard = findViewById(R.id.hp_card5);
        //customerSupportCard = findViewById(R.id.hp_card6);






        // Set click listeners
        assetManagementCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(f_home_page.this, g_ChapterListActivity.class));
            }
        });

        spareManagementCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(f_home_page.this, temp1.class));
            }
        });
//
//        maintenanceCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(f_home_page.this, Ca_Forklifts.class));
//            }
//        });
//
//        analyticsCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(f_home_page.this, Analytics.class));
//            }
//        });
//
//        customerSupportCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(f_home_page.this, Customer_support.class));
//            }
//        });

        settingsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(f_home_page.this, v_settings.class));
            }
        });


    }
}