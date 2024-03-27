package com.example.user;


import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CurrentOffers extends AppCompatActivity {

    ImageView home_btn, card_btn, account_btn;
    TextView titleOffers, currentOffers, userPoints;
    ArrayList<MyDataSetCurrentOffers> dataSets = new ArrayList<>();
    RecyclerView recyclerView;
    ButtonsOnManyActivities buttonsOnManyActivities;
    int currentPoints = 0;

    int id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_offers);

        home_btn = findViewById(R.id.homeBtnRewards);
        card_btn = findViewById(R.id.pointsCard);
        account_btn = findViewById(R.id.userAccountLogo);
        titleOffers = findViewById(R.id.titleOffers);
        currentOffers = findViewById(R.id.currentOffers);
        userPoints = findViewById(R.id.userPoints);


        // Initialize DB
        MyDBHelper dbHelper = new MyDBHelper(getApplicationContext());

        // Get User id
        Bundle extras = getIntent().getExtras();
        id = -1;
        if (extras != null){
            id = extras.getInt("id");
        }
        Log.d("CurrentOffers", "Received user ID: " + id);

        //User Points message
        currentPoints = dbHelper.getPointsValue(id);
        String pointsString = getString(R.string.user_points);
        String formattedText = currentPoints + " " + pointsString;
        String youHave = "You have";
        userPoints.setText(youHave + " " + formattedText);


        //Navigation bar
        buttonsOnManyActivities = new ButtonsOnManyActivities(this);
        buttonsOnManyActivities.HomeButton(this,home_btn); //home button
        buttonsOnManyActivities.account(this,account_btn); //account button
        buttonsOnManyActivities.pointsCard(this,card_btn); //card button

        //Recycle View Offers
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        dataSets.add(new MyDataSetCurrentOffers("Free Hot Drink (100 Points)", R.drawable.sample_logo));
        dataSets.add(new MyDataSetCurrentOffers("50% of Order (500 Points)", R.drawable.sample_logo));
        dataSets.add(new MyDataSetCurrentOffers("BOGO Food Items (150 Points)", R.drawable.sample_logo));

        MyAdapterCurrentOffers myAdapter = new MyAdapterCurrentOffers(dataSets, CurrentOffers.this, id, userPoints);
        recyclerView.setAdapter(myAdapter);

    }
}