package com.example.imma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.ObjectInputValidation;

public class Confirm_password extends AppCompatActivity {
    private EditText edit_pin1, edit_pin2;
    private Button confirm_button, cancel_button;
    private DatabaseHelper databaseHelper;
    private NestedScrollView nestedScrollView;
    private SQLiteDatabase database;

    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_password);

        cancel_button=findViewById(R.id.btn_cancel_pass);
        confirm_button=findViewById(R.id.btn_new_pass);
        edit_pin1 =findViewById(R.id.new_pin1);
        edit_pin2=findViewById(R.id.new_pin2);

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
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
    private void updatePassword() {
        String value1 = edit_pin1.getText().toString().trim();
        String value2 = edit_pin2.getText().toString().trim();

        if (value1.isEmpty()){
            edit_pin1.setError("Please fill the new password field");
            edit_pin1.requestFocus();
            return;
        }
        else if (value2.isEmpty()){
            edit_pin2.setError("Confirm new password field is empty");
            edit_pin2.requestFocus();
            return;
        }
        else if (!value1.contentEquals(value2)){
            Toast.makeText(this, "Password does not match, please try again", Toast.LENGTH_SHORT).show();
            edit_pin1.setText("");
            edit_pin2.setText("");
            edit_pin1.requestFocus();
            return;
        }else {
            pass=edit_pin1.getText().toString().trim();
            databaseHelper = new DatabaseHelper(getApplicationContext());
            database = databaseHelper.getWritableDatabase();
            databaseHelper.updatePassword(pass, value1, database);
            Toast.makeText(this, "Password reset successfully", Toast.LENGTH_SHORT).show();
            emptyEditText();
            Intent intent=new Intent(this, Login.class);
            startActivity(intent);
            finish();
            databaseHelper.close();
        }
    }
    private void emptyEditText() {
        edit_pin1.setText("");
        edit_pin2.setText("");
        edit_pin1.requestFocus();
    }
}