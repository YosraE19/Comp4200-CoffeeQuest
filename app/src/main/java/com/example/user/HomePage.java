package com.example.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
    TextView welcomeText;
    RecyclerView recyclerView;
    AdapterForHomePage adapter;
    ArrayList<DataSetHomePage> dataSets = new ArrayList<>();
    ImageView homeButton;
    ImageView pointsCardButton;
    ImageView accountButton;
    ButtonsOnManyActivities buttonsOnManyActivities;
    MyDBHelper myDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);

        myDBHelper = new MyDBHelper(this);

        Bundle extras = getIntent().getExtras();
        int id = -1;
        if (extras != null){
            id = extras.getInt("id");
        }

        welcomeText = findViewById(R.id.welcomeText);
        System.out.println("id: " + id);

        homeButton = findViewById(R.id.homeButtonOrder);
        pointsCardButton = findViewById(R.id.pointsCard);
        accountButton = findViewById(R.id.userAccountLogo);

        buttonsOnManyActivities = new ButtonsOnManyActivities(this);
        buttonsOnManyActivities.HomeButton(this, homeButton);
        buttonsOnManyActivities.pointsCard(this, pointsCardButton);
        buttonsOnManyActivities.account(this, accountButton);

        String nickname = myDBHelper.getUserNickname(id);
        String welcomeMessage = "Welcome ";
        if (nickname != null) {
            welcomeMessage += nickname;
        }

        welcomeText.setText(welcomeMessage);

        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        dataSets.add(new DataSetHomePage("Rewards and Points", R.drawable.rewards_and_points));
        dataSets.add(new DataSetHomePage("Offers and Discounts", R.drawable.offers_and_discounts));
        dataSets.add(new DataSetHomePage("Order", R.drawable.order));

        adapter = new AdapterForHomePage(dataSets, this);
        recyclerView.setAdapter(adapter);
    }
}