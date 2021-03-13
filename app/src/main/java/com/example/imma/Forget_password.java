package com.example.imma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Layout;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class Forget_password extends AppCompatActivity {
    private EditText confirm_email;
    private Button confirm_button, cancel_button;
    private DatabaseHelper databaseHelper;
    private NestedScrollView nestedScrollView;
    //private InputValidation inputValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        nestedScrollView=findViewById(R.id.nested);
        confirm_button=findViewById(R.id.btn_confirm_email);
        confirm_email=findViewById(R.id.edtMail);
        cancel_button=findViewById(R.id.cancel_insert);

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        databaseHelper=new DatabaseHelper(this);
        //inputValidation = new InputValidation(this);
        //setTitle("Recover password");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        confirm_button.setOnClickListener(ConfirmPasswordListener);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void emptyInputEditText(){
        confirm_email.setText("");
        confirm_email.requestFocus();
    }

    View.OnClickListener ConfirmPasswordListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (confirm_email.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Please enter your email address", Toast.LENGTH_SHORT).show();
                confirm_email.setText("");
                confirm_email.requestFocus();
                return;
            }
            else {
                SQLiteDatabase database;
                String email_store = confirm_email.getText().toString().trim();
                databaseHelper = new DatabaseHelper(getApplicationContext());
                database = databaseHelper.getReadableDatabase();
                Cursor cursor = databaseHelper.getEmail(email_store, database);

                if (cursor.moveToFirst()) {
                    String email = cursor.getString(0);
                    if ((confirm_email.getText().toString().trim().equals(email))) {
                        startActivity(new Intent(Forget_password.this, Confirm_password.class));
                    }
                    else if(!confirm_email.getText().toString().trim().equals(email)){
                        Toast.makeText(Forget_password.this, "Email address cannot be found, please try again", Toast.LENGTH_SHORT).show();
                        confirm_email.setText("");
                        confirm_email.requestFocus();
                    }
                    else {
                        databaseHelper.close();
                    }
                }
            }
        }
    };
}