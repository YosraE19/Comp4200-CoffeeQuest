package com.example.user;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RewardsPage extends AppCompatActivity {

    ImageView back_btn, home_btn;
    TextView titleRewards, welcomeText, userText ;
    FrameLayout frame;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rewards_page);


        back_btn = findViewById(R.id.backBtnRewards);
        home_btn = findViewById(R.id.homeBtnRewards);
        titleRewards = findViewById(R.id.titleRewards);
        welcomeText = findViewById(R.id.welcomeText);
        userText = findViewById(R.id.userText);
        frame = findViewById(R.id.frame);
        progressBar = findViewById(R.id.progressBar);

        String loggedInUser = "John Doe"; // To be configured to set as logged in user
        userText.setText(loggedInUser);

        //TODO create function to intialize progree bar based on Rewards table User ID

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}