package com.example.user;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CurrentOffers extends AppCompatActivity {

    ImageView home_btn, card_btn, account_btn;
    TextView titleOffers;
    ArrayList<MyDataSetCurrentOffers> dataSets = new ArrayList<>();
    RecyclerView recyclerView;
    ButtonsOnManyActivities buttonsOnManyActivities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_offers);

        home_btn = findViewById(R.id.homeBtnRewards);
        card_btn = findViewById(R.id.pointsCard);
        account_btn = findViewById(R.id.userAccountLogo);
        titleOffers = findViewById(R.id.titleOffers);

        // Initialize DB
        MyDBHelper dbHelper = new MyDBHelper(getApplicationContext());

        // TODO uncomment once nav bar functionality is complete
        /*Navigation bar
        buttonsOnManyActivities = new ButtonsOnManyActivities(this);
        buttonsOnManyActivities.HomeButton(this,home_btn); //home button
        buttonsOnManyActivities.account(this,account_btn); //account button
        buttonsOnManyActivities.pointsCard(this,card_btn); //card button
        */

        //Recycle View Offers
        recyclerView = findViewById(R.id.recyler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        dataSets.add(new MyDataSetCurrentOffers("Free Hot Drink", R.drawable.sample_logo));
        dataSets.add(new MyDataSetCurrentOffers("50% of Order", R.drawable.sample_logo));
        dataSets.add(new MyDataSetCurrentOffers("BOGO Food Items", R.drawable.sample_logo));

        MyAdapterCurrentOffers myAdapter = new MyAdapterCurrentOffers(dataSets, CurrentOffers.this);
        recyclerView.setAdapter(myAdapter);


    }
}