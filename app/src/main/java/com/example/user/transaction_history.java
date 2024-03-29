package com.example.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class transaction_history extends AppCompatActivity {
    ImageView back_btn, home_btn, card_btn,account_btn;
    Button get_points_btn, rewards_btn, current_offers_btn, recentTranaction_btn;
    ButtonsOnManyActivities buttonsOnManyActivities;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transaction_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonsOnManyActivities = new ButtonsOnManyActivities(this);

        //back_btn = findViewById(R.id.backButton);
        home_btn = findViewById(R.id.homeButtonOrder);
        card_btn = findViewById(R.id.pointsCard);
        account_btn = findViewById(R.id.userAccountLogo);

        // Receive user id
        Bundle extras = getIntent().getExtras();
        id = -1;
        if (extras != null) {
            id = extras.getInt("id");
        }
        Log.d("Order", "Received user ID: " + id);

        //When the home button is pressed it will take the user to the home page:
        //call the home button listener function from the ButtonsOnManyActivities
        buttonsOnManyActivities.HomeButton(this,home_btn);

        //When the point card is pressed it will take the user to the points card fragment
        //call the points card listener from the ButtonsOnManyActivities
        buttonsOnManyActivities.pointsCard(this,card_btn);

        //When the user account button is pressed it will take the user to the account page
        //call the account button listener from the ButtonsOnManyActivities
        buttonsOnManyActivities.account(this,account_btn, id);
    }
}