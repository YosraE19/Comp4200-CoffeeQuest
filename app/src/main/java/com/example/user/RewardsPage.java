package com.example.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    ImageView home_btn, card_btn, account_btn;
    Button offers_btn;
    TextView titleRewards, welcomeText, userText, userPoints;
    FrameLayout frame;
    ProgressBar progressBar;
    int currentPoints = 0;
    int id = 0;
    ButtonsOnManyActivities buttonsOnManyActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards_page);
        EdgeToEdge.enable(this);

        //initialize views
        home_btn = findViewById(R.id.homeBtnRewards);
        card_btn = findViewById(R.id.pointsCard);
        account_btn = findViewById(R.id.userAccountLogo);
        titleRewards = findViewById(R.id.titleRewards);
        welcomeText = findViewById(R.id.welcomeText);
        userText = findViewById(R.id.userText);
        frame = findViewById(R.id.frame);
        progressBar = findViewById(R.id.progressBar);
        userPoints = findViewById(R.id.userPoints);
        offers_btn = findViewById(R.id.currentOffersBtn);

        // Initialize DB
        MyDBHelper dbHelper = new MyDBHelper(getApplicationContext());

        // Initialize Action Bar
        buttonsOnManyActivities = new ButtonsOnManyActivities(this);
        buttonsOnManyActivities.HomeButton(this,home_btn); //home button
        buttonsOnManyActivities.account(this,account_btn); //account button
        buttonsOnManyActivities.pointsCard(this,card_btn); //card button

        //Receive user id
        Bundle extras = getIntent().getExtras();
        id = -1;
        if (extras != null){
            id = extras.getInt("id");
        }
        Log.d("RewardPage", "Received user ID: " + id);

        // Get userNickname for welcome message
        String userNickname = dbHelper.getUserNickname(id);
        userText.setText(userNickname);

        // Initialize progress bar with points for _idUser
        currentPoints = dbHelper.getPointsValue(id);
        progressBar.setProgress(currentPoints);
        String pointsString = getString(R.string.user_points);
        String formattedText = currentPoints + " " + pointsString;
        userPoints.setText(formattedText);

        // Apply window insets listener for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set onClickListener for Current Offers button
        offers_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open HomePage
                Intent intent = new Intent(RewardsPage.this, CurrentOffers.class);
                intent.putExtra("id", id); // Pass the user ID to CurrentOffers
                startActivity(intent);
                finish();
            }
        });
    }
}
