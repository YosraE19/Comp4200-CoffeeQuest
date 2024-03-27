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
    TextView titleOffers, currentOffers, currentPoints;
    ArrayList<MyDataSetCurrentOffers> dataSets = new ArrayList<>();
    RecyclerView recyclerView;
    ButtonsOnManyActivities buttonsOnManyActivities;

    int currentUserPoints = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_offers);

        home_btn = findViewById(R.id.homeBtnRewards);
        card_btn = findViewById(R.id.pointsCard);
        account_btn = findViewById(R.id.userAccountLogo);
        titleOffers = findViewById(R.id.titleOffers);
        currentPoints = findViewById(R.id.currentPoints);
        currentOffers = findViewById(R.id.currentOffers);

        // Initialize DB
        MyDBHelper dbHelper = new MyDBHelper(getApplicationContext());

        // Get User id
        Bundle extras = getIntent().getExtras();
        int id = -1;
        if (extras != null){
            id = extras.getInt("id");
        }

        // TODO uncomment once nav bar functionality is complete
        //Navigation bar
        buttonsOnManyActivities = new ButtonsOnManyActivities(this);
        buttonsOnManyActivities.HomeButton(this,home_btn); //home button
        buttonsOnManyActivities.account(this,account_btn); //account button
        buttonsOnManyActivities.pointsCard(this,card_btn); //card button

        // Initialize progress bar with points for _idUser
        currentUserPoints = dbHelper.getPointsValue(id); // TODO: Modify to select logged-in _idUser
        String pointsString = getString(R.string.user_points);
        String formattedText = currentUserPoints + " " + pointsString;
        currentPoints.setText(formattedText);

        //Recycle View Offers
        recyclerView = findViewById(R.id.recyler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        dataSets.add(new MyDataSetCurrentOffers("Free Hot Drink", R.drawable.sample_logo));
        dataSets.add(new MyDataSetCurrentOffers("50% of Order", R.drawable.sample_logo));
        dataSets.add(new MyDataSetCurrentOffers("BOGO Food Items", R.drawable.sample_logo));

        MyAdapterCurrentOffers myAdapter = new MyAdapterCurrentOffers(dataSets, CurrentOffers.this, id);
        recyclerView.setAdapter(myAdapter);


    }
}