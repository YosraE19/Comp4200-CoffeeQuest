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

public class ResetPwd extends AppCompatActivity {

    Button reset_btn;
    EditText resetEmail, resetPassword;

    MyDBHelper dbHelper; // Declare here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_pwd);

        // Initialize DB here
        dbHelper = new MyDBHelper(getApplicationContext());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btn_signup), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        resetEmail = findViewById(R.id.emailReset);
        resetPassword = findViewById(R.id.passwordReset);
        reset_btn = findViewById(R.id.reset_btn);

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resetEmailStr = resetEmail.getText().toString();
                String resetPasswordStr = resetPassword.getText().toString();

                // Check if the reset email is more than 6 characters
                if (resetPasswordStr.length() <= 6) {
                    Toast.makeText(ResetPwd.this, "New password must be more than 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the reset email exists in the database
                if (!dbHelper.isEmailExists(resetEmailStr)) {
                    Toast.makeText(ResetPwd.this, "Email does not exist", Toast.LENGTH_SHORT).show();
                    return;
                }

                dbHelper.updatePassword(resetEmailStr, resetPasswordStr);
                Toast.makeText(ResetPwd.this, "Password has been reset", Toast.LENGTH_SHORT).show();

                // Start MainActivity and finish ResetPwd activity
                Intent intent = new Intent(ResetPwd.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}
