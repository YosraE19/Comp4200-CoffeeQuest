package com.example.user;

import android.os.Bundle;
import android.util.Log;
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

    int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_info_page);

        home_btn = findViewById(R.id.homeBtnRewards);
        card_btn = findViewById(R.id.pointsCard);
        account_btn = findViewById(R.id.userAccountLogo);
        titleCafe = findViewById(R.id.titleCafe);
        // Initialize DB
        MyDBHelper dbHelper = new MyDBHelper(getApplicationContext());

        // Get User id
        Bundle extras = getIntent().getExtras();
        id = -1;
        if (extras != null){
            id = extras.getInt("id");
        }
        Log.d("CafeInfoPage", "Received user ID: " + id);

        //Navigation bar
        buttonsOnManyActivities = new ButtonsOnManyActivities(this);
        buttonsOnManyActivities.HomeButton(this,home_btn); //home button
        buttonsOnManyActivities.account(this,account_btn, id); //account button
        buttonsOnManyActivities.pointsCard(this,card_btn); //card button

        //Recycle View Offers
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2); // 2 columns
        recyclerView.setLayoutManager(layoutManager);

        dataSets.add(new MyDataSetCafeInfo("TIIMS", R.drawable.cafe_1));
        dataSets.add(new MyDataSetCafeInfo("LUNA NOVA", R.drawable.cafe_2));
        dataSets.add(new MyDataSetCafeInfo("MEOW COFFEEE", R.drawable.cafe_3));
        dataSets.add(new MyDataSetCafeInfo("MAC CAFFE", R.drawable.cafe_4));

        MyAdapterCafeInfo myAdapter = new MyAdapterCafeInfo(dataSets, CafeInfoPage.this);
        recyclerView.setAdapter(myAdapter);

    }
}