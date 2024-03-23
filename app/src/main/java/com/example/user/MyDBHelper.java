package com.example.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyDBHelper extends SQLiteOpenHelper {

    public MyDBHelper(@Nullable Context context) {
        super(context, "COMP4200DB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating UserTable
        String createUserQuery = "CREATE TABLE userTable(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userEmail VARCHAR(100) not null unique, " +
                "userNickname VARCHAR(50) not null,  " +
                "userPassword VARCHAR(20) not null)";
        db.execSQL(createUserQuery);
        Log.d("MyDBHelper", "UserTable created");

        // Creating Transaction Table
        String createTransactionQuery = "CREATE TABLE transactionTable(_idTransaction INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "transactionTotal NUMERIC NOT NULL, " +
                "converted BOOLEAN DEFAULT 0)";
        db.execSQL(createTransactionQuery);
        Log.d("MyDBHelper", "TransactionTable created");

        // Creating Rewards Table with foreign key constraint
        String createRewardsQuery = "CREATE TABLE rewardsTable(_idReward INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "points INTEGER NOT NULL, "
                + "_idUser INTEGER, "
                + "FOREIGN KEY(_idUser) REFERENCES user(_id)"
                + ")";
        db.execSQL(createRewardsQuery);
        Log.d("MyDBHelper", "RewardsTable created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /* Drop the existing tables if they exist
        db.execSQL("DROP TABLE IF EXISTS userTable");
        db.execSQL("DROP TABLE IF EXISTS transactionTable");
        db.execSQL("DROP TABLE IF EXISTS rewardsTable");

        // Recreate the tables by calling onCreate
        onCreate(db);*/
    }


    //METHODS FOR ADDING DB RECORDS
    // Method to add a new user to the database
    // Method to add a new user to the database
    public void addUser(String userEmail, String userNickname, String userPassword) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if the user with the same email already exists
        Cursor cursor = db.rawQuery("SELECT * FROM userTable WHERE userEmail=?", new String[]{userEmail});
        if (cursor.getCount() > 0) {
            // User with the same email already exists, handle accordingly (e.g., show a message)
            Log.d("MyDBHelper", "User with email " + userEmail + " already exists");
        } else {
            // User with the same email doesn't exist, proceed with insertion
            ContentValues values = new ContentValues();
            values.put("userEmail", userEmail);
            values.put("userNickname", userNickname);
            values.put("userPassword", userPassword);
            // Inserting Row
            long newRowId = db.insert("userTable", null, values);
            Log.d("MyDBHelper", "New user inserted with ID: " + newRowId);
        }
        cursor.close();
    }


    // Method to add a new transaction to the database
    public void addTransaction(double transactionTotal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("transactionTotal", transactionTotal);
        values.put("converted", 0); // Setting the value to false (0)
        // Inserting Row
        long newRowId = db.insert("transactionTable", null, values);
    }

    // Method to add a new reward to the database
    public void addReward(int points, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("points", points);
        values.put("_idUser", userId);
        // Inserting Row
        long newRowId = db.insert("rewardsTable", null, values);
    }



}
