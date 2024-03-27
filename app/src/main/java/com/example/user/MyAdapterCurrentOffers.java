package com.example.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapterCurrentOffers extends RecyclerView.Adapter<MyAdapterCurrentOffers.MyViewHolder> {

    ArrayList<MyDataSetCurrentOffers> dataList;
    Context context;
    private int id;
    MyDBHelper dbHelper;

    TextView userPoints;

    int offerPoints, currentPoints, updatedPoints; //variables to check if user has enough points
    public MyAdapterCurrentOffers(ArrayList<MyDataSetCurrentOffers> data, Context context, int id, TextView userPoints) {
        this.dataList = data;
        this.context = context;
        this.id = id;
        this.userPoints = userPoints;
        dbHelper = new MyDBHelper(context);
        currentPoints = dbHelper.getPointsValue(id);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_current_offer, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyDataSetCurrentOffers data = dataList.get(position);
        holder.imageView.setImageResource(data.getImage());
        holder.textView.setText(data.getText());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offerPoints = getOfferPoints(position);
                if (currentPoints >= offerPoints) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Redeem Offer");
                    builder.setMessage("Do you want to redeem offer " + (position + 1) + "?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            //update user Reward profile with new point balance
                            updatedPoints = currentPoints - offerPoints;
                            dbHelper.updateRewardsPoints(id, updatedPoints);
                            currentPoints = dbHelper.getPointsValue(id);

                            String msg = "Offer " + (position + 1) + " redeemed!";
                            Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                            // Refresh userPoints TextView with new points value
                            refreshUserPoints();

                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // No action needed
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    Toast.makeText(context, "You do not have enough points!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Refresh userPoints TextView with new points value
    private void refreshUserPoints() {
        currentPoints = dbHelper.getPointsValue(id);
        String pointsString = context.getString(R.string.user_points);
        String formattedText = currentPoints + " " + pointsString;
        userPoints.setText("You have " + formattedText);
    }

    //Assigning offer Points based on position
    private int getOfferPoints(int position) {
        // Return the offer points based on the position
        switch (position) {
            case 0:
                return 100;
            case 1:
                return 500;
            case 2:
                return 150;
            default:
                return 0;
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textView = itemView.findViewById(R.id.tv_card);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}



