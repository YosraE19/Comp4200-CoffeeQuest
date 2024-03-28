package com.example.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUpPage extends AppCompatActivity {

    EditText nickname, email, password;
    Button sign_up;

    MyDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_sign_up_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.nicknameTitle), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
          nickname = findViewById(R.id.nickname);
          email = findViewById(R.id.email);
          password = findViewById(R.id.confirmNewPwd);
          sign_up = findViewById(R.id.createAccount);


        MyDBHelper dbHelper = new MyDBHelper(getApplicationContext());

        //Creates a new user.
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Makes sure no fields are left empty.
                if(email.equals("")||nickname.equals("")||password.equals("")){
                    Toast.makeText(SignUpPage.this, "Please fill in required info", Toast.LENGTH_LONG).show();
                }
                else {
                    //Password must be at least 7 characters long
                    if(password.length()>6)
                    try {
                        dbHelper.addUser(email.getText().toString(), nickname.getText().toString(), password.getText().toString());
                        Intent toLogin = new Intent(SignUpPage.this, MainActivity.class);
                        int id = dbHelper.getID(email.getText().toString());
                        dbHelper.addReward(10, id);
                        startActivity(toLogin);
                    }catch (Exception e){

                    }
                    else {
                        Toast.makeText(SignUpPage.this, "Password must be at least 6 chars", Toast.LENGTH_LONG).show();
                    }
                    }
            }
        });
    }

}