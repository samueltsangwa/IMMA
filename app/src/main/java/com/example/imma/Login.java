package com.example.imma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private TextView reset,  user_register;
    private EditText username, password;
    private Button bn_login;
    private String user_name, pass;
    private ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user_register = findViewById(R.id.register);
        bn_login = findViewById(R.id.btn_login);
        username = findViewById(R.id.user_name);
        password = findViewById(R.id.password);
        reset = findViewById(R.id.log);
        loading=findViewById(R.id.loading);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Forget_password.class));
            }
        });

        bn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(); //call when button is clicked to validate the input
            }
        });

        user_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void login() {
        if (validateUser()) {
            DatabaseHelper databaseHelper = new DatabaseHelper(Login.this);
            Cursor cursor = databaseHelper.loginCheck(user_name, pass);
            if (cursor.moveToFirst()) {
                finish();
                cursor.close();
                startActivity(new Intent(this, Dashboard.class));
            } else {
                cursor.close();
                Toast.makeText(this, "Username or password incorrect, please try again", Toast.LENGTH_SHORT).show();
                username.setText("");
                username.requestFocus();
                password.setText("");
            }
        }
    }
    private boolean validateUser() {
        user_name = username.getText().toString().trim();
        pass = password.getText().toString().trim();
        if (user_name.isEmpty()) {
            username.setError("Username field cannot be empty");
            username.requestFocus();
            return false;
        }
        else if (pass.isEmpty()) {
            password.setError("Password filed cannot be empty");
            password.requestFocus();
            return false;
        } else {
            username.setError(null);
            password.setError(null);
            return true;
        }
    }
}
