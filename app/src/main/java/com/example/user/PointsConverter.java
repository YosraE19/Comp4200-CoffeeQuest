package com.example.user;

import android.util.Log;

public class PointsConverter {


    //Get the database:
    private static MyDBHelper myDBHelper;

    // Initialize myDBHelper
    public static void init(MyDBHelper dbHelper) {
        myDBHelper = dbHelper;
    }
    //Function to convert between Dollars to Points
    //For every dollar = 10 points
    public static int convertDollarToPoints (int transactionID, int userId, double amount){
        myDBHelper.updateConvertColumn((transactionID));
        //Calculate the points
        int points = (int)(amount * 10);
        Log.d ("Points Converter","Points = " + points);
        //take the user id to get the total current user rewards
        int currentRewards = myDBHelper.getPointsValue(userId);
        Log.d ("Points Converter","Current Points = " + currentRewards);
        //add to teh user's total rewards:
        int newRewards = currentRewards + points;
        Log.d ("Points Converter","Updated total points = " + newRewards);
        //update the rewards database:
        myDBHelper.updateRewardsPoints(userId, newRewards);

        return points;
    }

}
