package com.example.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/*
 * Account - will contain :
 *  navbar - navigates to home, points card, and account
 *  change profile pic
 *  enter & save new nickname and email
 *  log out of account
 * */


public class Account extends AppCompatActivity {

    ImageView homeBtn, pointsBtn, accountBtn;
    Button logOutBtn, saveBtn;
    FrameLayout frame;
    ButtonsOnManyActivities buttonsOnManyActivities;
    //here i want to somehow click change pfp and have it open files and ask me to upload a new one

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.nicknameTitle), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonsOnManyActivities = new ButtonsOnManyActivities(this);

        homeBtn = findViewById(R.id.homeButtonOrder);
        pointsBtn = findViewById(R.id.pointsCard);
        accountBtn = findViewById(R.id.userAccountLogo);
        logOutBtn = findViewById(R.id.logOutBtn);
        saveBtn = findViewById(R.id.saveBtn);
        frame = findViewById(R.id.frame);

        buttonsOnManyActivities = new ButtonsOnManyActivities(this);
        buttonsOnManyActivities.HomeButton(this, homeBtn);
        buttonsOnManyActivities.pointsCard(this, pointsBtn);

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent toMain = new Intent(Account.this, MainActivity.class);
                startActivity(toMain);
                finish();
            }
        });


        //nav bar functionality
//        homeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent goHome = new Intent(Account.this, HomePage.class);
//                startActivity(goHome);
//            }
//        });
//
//        pointsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent goPoints = new Intent(Account.this, Points_Card.class);
//                startActivity(goPoints);
//            }
//        });
//
//        accountBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }
}