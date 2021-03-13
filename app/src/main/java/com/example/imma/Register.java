package com.example.imma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private EditText fi_name, la_name, email_address, user, confirmpassw, password;
    private Button reg;
    private ImageView image;
    private ProgressBar loading;
    private static final String URL_REGIST = "http://192.168.43.145/imma_project/register.php";
    private String f_name, l_name, email_add, user_name, passw, confirmpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Assign variables
        loading = findViewById(R.id.loading);
        fi_name = findViewById(R.id.f_name);
        la_name = findViewById(R.id.l_name);
        email_address = findViewById(R.id.email_add);
        user = findViewById(R.id.user_name);
        password = findViewById(R.id.passw);
        confirmpassw = findViewById(R.id.confirm_pass);
        Button cancel = findViewById(R.id.cancel_reg);
        reg = findViewById(R.id.btnregister);
        TextView login = findViewById(R.id.log);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(); //call when button is clicked to validate the input
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void register() {
        loading.setVisibility(View.VISIBLE);
        reg.setVisibility(View.GONE);
        getInputs(); // getInputs the input to string variables
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if (success.equals("1")) {
                        Toast.makeText(Register.this, "You have been successfully registered", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Register.this, "Registration failed" + e.toString(), Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    reg.setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(Register.this, "Registration failed" + volleyError.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        reg.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fi_name", f_name);
                params.put("la_name", l_name);
                params.put("email_address", email_add);
                params.put("user", user_name);
                params.put("password", passw);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void getInputs() {
        f_name=fi_name.getText().toString().trim();
       l_name = la_name.getText().toString().trim();
       email_add = email_address.getText().toString().trim();
       user_name = user.getText().toString().trim();
       passw= password.getText().toString().trim();
       confirmpass = confirmpassw.getText().toString().trim();
    }

    public boolean validate() {
        if (f_name.isEmpty()) {
            fi_name.setError("First name field is empty, please fill it");
            fi_name.requestFocus();
            return false;
        }
        if (f_name.contains("0-9")) {
            fi_name.setError("Invalid name, please try another one");
            fi_name.requestFocus();
            return false;
        }
        if (l_name.isEmpty()) {
            la_name.setError("Last name field is empty, please fill it");
            la_name.requestFocus();
            return false;
        }
        if (l_name.contains("0-9")) {
            la_name.setError("Invalid name, Ensure your name contains characters only");
            la_name.requestFocus();
            return false;
        }
        if (email_add.isEmpty()) {
            email_address.setError("Please enter email address");
            email_address.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email_add).matches()) {
            email_address.setError("Please ensure you type a valid email address");
            email_address.requestFocus();
            return false;
        }
        if (user_name.isEmpty()) {
            user.setError("Username field is empty,Please fill the field");
            user.requestFocus();
            return false;
        }
        if (passw.isEmpty()) {
            password.setError("Password field is empty,Please fill the field");
            password.requestFocus();
            return false;
        }
        if (passw.length() < 5) {
            password.setError("Password is too short,Please ensure your password have more than five characters");
            password.requestFocus();
            return false;
        }
        if (confirmpass.isEmpty()) {
            confirmpassw.setError("Confirm password field is empty,Please fill the field");
            confirmpassw.requestFocus();
            return false;
        }
        if (!passw.equals(confirmpass)) {
            Toast.makeText(this, "Password mismatch, please try again", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
