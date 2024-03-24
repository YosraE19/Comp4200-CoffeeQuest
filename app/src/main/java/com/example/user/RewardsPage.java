package com.example.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    ImageView home_btn;
    TextView titleRewards, welcomeText, userText;
    FrameLayout frame;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards_page);

        EdgeToEdge.enable(this);

        // Initialize views
        home_btn = findViewById(R.id.homeBtnRewards);
        titleRewards = findViewById(R.id.titleRewards);
        welcomeText = findViewById(R.id.welcomeText);
        userText = findViewById(R.id.userText);
        frame = findViewById(R.id.frame);
        progressBar = findViewById(R.id.progressBar);

        // Initialize DB
        MyDBHelper dbHelper = new MyDBHelper(getApplicationContext());

        // Get userNickname for welcome message
        String userNickname = dbHelper.getUserNickname(1); // TODO: Modify to select logged-in user _id
        userText.setText(userNickname);

        // Initialize progress bar with points for _idUser
        int points = dbHelper.getPointsValue(1); // TODO: Modify to select logged-in _idUser
        progressBar.setProgress(points);

        // Apply window insets listener for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set onClickListener for the home button
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open HomePage
                Intent intent = new Intent(RewardsPage.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
