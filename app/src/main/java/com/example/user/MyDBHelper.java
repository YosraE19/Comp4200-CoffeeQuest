package com.example.user;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

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
        //Drop the existing tables if they exist
        db.execSQL("DROP TABLE IF EXISTS userTable");
        db.execSQL("DROP TABLE IF EXISTS transactionTable");
        db.execSQL("DROP TABLE IF EXISTS rewardsTable");

        // Recreate the tables by calling onCreate
        onCreate(db);
    }


    //METHODS FOR ADDING DB RECORDS
    // Method to add a new user to the database
    public void addUser(String userEmail, String userNickname, String userPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userEmail", userEmail);
        values.put("userNickname", userNickname);
        values.put("userPassword", userPassword);

        // Inserting Row
        long newRowId = db.insert("userTable", null, values);
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

    //METHODS FOR USER TABLE
    //method for displaying userNickname on Rewards page
    public String getUserNickname(int userID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {"userNickname"};
        String selection = "_id = ?";
        String[] selectionArgs = {String.valueOf(userID)};
        Cursor cursor = db.query("userTable", projection, selection, selectionArgs, null, null, null);
        String userNickname = null;

        try {
            if (cursor != null && cursor.moveToFirst()) {
                int nicknameIndex = cursor.getColumnIndex("userNickname");
                if (nicknameIndex != -1) {
                    userNickname = cursor.getString(nicknameIndex);
                    Log.d("MyDBHelper", "User Nickname: " + userNickname);
                } else {
                    Log.e("MyDBHelper", "Column 'userNickname' not found in cursor.");
                }
            }
        } catch (Exception e) {
            Log.e("MyDBHelper", "Error fetching user nickname: " + e.getMessage());
        } finally {
            // Close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        return userNickname;
    }

    //METHODS FOR REWARDS TABLE
    //method for updating user points in Rewards table
    public void updateRewardsPoints(int userId, int points) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("points", points);
        String selection = " _idUser = ?";
        String[] selectionArgs = {String.valueOf(userId)};
        int rowsAffected = db.update("rewardsTable", values, selection, selectionArgs);
        Log.d("MyDBHelper", "Rows affected: " + rowsAffected);
    }

    //method for deleting record in Rewards table where _idRewards = value
    public void deleteReward(int rewardId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = "_idReward = ?";
        String[] selectionArgs = {String.valueOf(rewardId)};
        int rowsDeleted = db.delete("rewardsTable", selection, selectionArgs);
        Log.d("MyDBHelper", "Rows deleted: " + rowsDeleted);
    }


    // Method to get points value for a specific user ID
    public int getPointsValue(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        int pointsValue = 0;
        Cursor cursor = null;

        try {
            // Query to retrieve points value for the given user ID
            String query = "SELECT points FROM rewardsTable WHERE _idUSER = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

            // Check if the cursor has data
            if (cursor != null && cursor.moveToFirst()) {
                int pointsIndex = cursor.getColumnIndex("points");
                if (pointsIndex != -1) {
                    pointsValue = cursor.getInt(pointsIndex);
                    Log.d("MyDBHelper", "Points Value: " + pointsValue);
                } else {
                    Log.e("MyDBHelper", "Column 'points' not found in cursor.");
                }
            }
        } catch (Exception e) {
            Log.e("MyDBHelper", "Error fetching points value: " + e.getMessage());
        } finally {
            // Close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        return pointsValue;
    }


//METHODS FOR TRANSACTION TABLE
    //Get the transaction id and return the amount
    public double getAmountFromTransactionID(int transactionID) {
        SQLiteDatabase db = this.getReadableDatabase();
        double amount = 0;
        Cursor cursor = null;

        try {
            // Query to retrieve transaction total value for the given user ID
            String query = "SELECT transactionTotal FROM transactionTable WHERE _idTransaction = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(transactionID)});

            // Check if the cursor has data
            if (cursor != null && cursor.moveToFirst()) {
                int amountIndex = cursor.getColumnIndex("transactionTotal");
                if (amountIndex != -1) {
                    amount = cursor.getDouble(amountIndex);
                    Log.d("MyDBHelper", "Transaction Amount: " + amount);
                } else {
                    Log.e("MyDBHelper", "Column 'transactionTotal' not found in cursor.");
                }
            }
        } catch (Exception e) {
            Log.e("MyDBHelper", "Error fetching transaction amount: " + e.getMessage());
        } finally {
            // Close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        return amount;
    }
    //A method that checks if the amount has been converted to points or not:
    public boolean isPointsConverted(int transactionID) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean isConverted = false;
        Cursor cursor = null;

        try {
            // Query to retrieve points value for the given user ID
            String query = "SELECT transactionTotal FROM transactionTable WHERE _idTransaction = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(transactionID)});

            // Check if the cursor has data
            if (cursor != null && cursor.moveToFirst()) {
                //goes to the column called converted in the transaction table:
                int convertedIndex = cursor.getColumnIndex("converted");
                if (convertedIndex != -1) {
                    //points have been converted:
                    isConverted = cursor.getInt(convertedIndex) == 1;
                    Log.d("MyDBHelper", "Points Converted: " + isConverted);
                } else {
                    Log.e("MyDBHelper", "Column 'converted' not found in cursor.");
                }
            }
        } catch (Exception e) {
            Log.e("MyDBHelper", "Error fetching transaction amount: " + e.getMessage());
        } finally {
            // Close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        return isConverted;
    }
    public Boolean checkUserPass(String email, String pass) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = MyDB.rawQuery("Select * from userTable where userEmail = ? and userPassword = ?", new String[]{email, pass});
        }catch (Exception e){

        }

        if(cursor.getCount()>0) {
            return true;
        }
        else
            return false;
    }
    public int getID(String email){
        int id = -1;
        Cursor cursor = null;
        try {
            SQLiteDatabase MyDB = this.getReadableDatabase();
            cursor = MyDB.rawQuery("Select _id from userTable where userEmail = ?", new String[]{email});
            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex("_id");
                id = cursor.getInt(idIndex);
                if (idIndex != -1) {
                    id = cursor.getInt(idIndex);
                    Log.d("MyDBHelper", "ID received: " + id);
                } else {
                    Log.e("MyDBHelper", "Column '_id' not found in cursor.");
                }
            }
        }
        catch(Exception e){
            Log.e("MyDBHelper", "Error fetching id: " + e.getMessage());
        } finally {
            if(cursor!= null)
                cursor.close();
        }
        return id;
    }

}