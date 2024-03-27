package com.example.user;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CafeInfoPage extends AppCompatActivity {

    ImageView home_btn, card_btn, account_btn;
    TextView titleCafe;
    ArrayList<MyDataSetCafeInfo> dataSets = new ArrayList<>();
    ButtonsOnManyActivities buttonsOnManyActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_info_page);

        home_btn = findViewById(R.id.homeBtnRewards);
        card_btn = findViewById(R.id.pointsCard);
        account_btn = findViewById(R.id.userAccountLogo);
        titleCafe = findViewById(R.id.titleCafe);

        //Navigation bar
        buttonsOnManyActivities = new ButtonsOnManyActivities(this);
        buttonsOnManyActivities.HomeButton(this,home_btn); //home button
        buttonsOnManyActivities.account(this,account_btn); //account button
        buttonsOnManyActivities.pointsCard(this,card_btn); //card button

        //Recycle View Offers
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2); // 2 columns
        recyclerView.setLayoutManager(layoutManager);

        dataSets.add(new MyDataSetCafeInfo("Cafe 1", R.drawable.sample_logo));
        dataSets.add(new MyDataSetCafeInfo("Cafe 2", R.drawable.sample_logo));
        dataSets.add(new MyDataSetCafeInfo("Cafe 3", R.drawable.sample_logo));
        dataSets.add(new MyDataSetCafeInfo("Cafe 4", R.drawable.sample_logo));

        MyAdapterCafeInfo myAdapter = new MyAdapterCafeInfo(dataSets, CafeInfoPage.this);
        recyclerView.setAdapter(myAdapter);

    }
}