package com.example.imma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class Reset extends AppCompatActivity {
    private  EditText SearchName,Email,PassCode;
    private TextView show_user, show_pass;
    private Button Search;
    DatabaseHelper databaseHelper;
    SQLiteDatabase database;
    String email_name, pass_field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        SearchName=findViewById(R.id.txt_search_username);
        Search=findViewById(R.id.btnSearch_username);
        Email=findViewById(R.id.edtmail);
        PassCode=findViewById(R.id.edtpass);
        show_user = findViewById(R.id.a);
        show_pass= findViewById(R.id.c);

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
    private boolean validateSearchbox() {
        email_name = SearchName.getText().toString().trim();
        if (email_name.isEmpty()) {
            SearchName.setError("Email address field cannot empty");
            SearchName.requestFocus();
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email_name).matches()) {
            SearchName.setError("Please ensure you type a valid email address");
            SearchName.requestFocus();
            return false;
        }
        else {
            SearchName.setError(null);
            return true;
        }
    }
    public void SearchUsername(View view) {
        if (validateSearchbox()) {
            email_name = SearchName.getText().toString().trim();
            databaseHelper = new DatabaseHelper(getApplicationContext());
            database = databaseHelper.getReadableDatabase();
            Cursor cursor = databaseHelper.getUsername(email_name, database);
            if (cursor.moveToFirst()) {
                String email = cursor.getString(0);
                String pass = cursor.getString(1);
                show_user.setText("Your registered username is :");
                show_pass.setText("Your old password is :");
                Email.setText(email);
                PassCode.setText(pass);
            }
            else {
                cursor.close();
                Toast.makeText(this, "The email address entered cannot be found, please try with another one", Toast.LENGTH_SHORT).show();
                SearchName.setText("");
                SearchName.requestFocus();
            }
        }
    }
    private boolean validateUpdatePassword() {
        pass_field = PassCode.getText().toString().trim();
        email_name = SearchName.getText().toString().trim();
        if (pass_field.isEmpty()) {
            PassCode.setError("Password filed cannot be empty");
            PassCode.requestFocus();
            return false;
        }
        else if (email_name.isEmpty()) {
            SearchName.setError("Email address filed cannot be empty");
            SearchName.requestFocus();
            return false;
        }
        else {
            return true;
        }
    }
        public void UpdatePassword (View view) {
            if (validateUpdatePassword()) {
                String password = PassCode.getText().toString().trim();
                databaseHelper = new DatabaseHelper(getApplicationContext());
                database = databaseHelper.getWritableDatabase();
                databaseHelper.updatePassword(email_name, password, database);
            }
            Toast.makeText(this, "Password has been changed successfully", Toast.LENGTH_SHORT).show();
            SearchName.setText("");
            Email.setText("");
            PassCode.setText("");
            SearchName.requestFocus();
        }
}


