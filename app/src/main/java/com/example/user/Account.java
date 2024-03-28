package com.example.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

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
    int id;
    ImageView homeBtn, pointsBtn, accountBtn;
    Button logOutBtn, saveBtn;
    FrameLayout frame;
    ButtonsOnManyActivities buttonsOnManyActivities;

    EditText newEmail, newNickname;

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
        MyDBHelper dbHelper = new MyDBHelper(getApplicationContext());
        buttonsOnManyActivities = new ButtonsOnManyActivities(this);
        newEmail = findViewById(R.id.getEmail);
        newNickname = findViewById(R.id.getNickname);
        homeBtn = findViewById(R.id.homeButtonOrder);
        pointsBtn = findViewById(R.id.pointsCard);
        accountBtn = findViewById(R.id.userAccountLogo);
        logOutBtn = findViewById(R.id.logOutBtn);
        saveBtn = findViewById(R.id.saveBtn);
        frame = findViewById(R.id.frame);


        // Get User id
        Bundle extras = getIntent().getExtras();
        id = -2;
        if (extras != null){
            id = extras.getInt("id");
        }
        Log.d("Account", "Received user ID: " + id);

        //Updates user information in Database.
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //Case handling if user leaves one or both fields empty.
                    if(newNickname.getText().toString().equals("") && newEmail.getText().toString().equals("")){
                        Toast.makeText(Account.this, "Please fill one or both fields", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (newNickname.getText().toString().equals("")) {
                        } else
                            dbHelper.updateNickname(id, newNickname.getText().toString());
                        if (newEmail.getText().toString().equals("")) {
                        } else
                            dbHelper.updateEmail(id, newEmail.getText().toString());
                    }
            }
        });

        buttonsOnManyActivities = new ButtonsOnManyActivities(this);
        buttonsOnManyActivities.HomeButton(this, homeBtn);
        buttonsOnManyActivities.pointsCard(this, pointsBtn);

        //Logs user out then ends context.
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