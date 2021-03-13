package com.example.imma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.security.PrivateKey;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView login, register;
    private CharSequence[] charSequences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login=findViewById(R.id.card_log);
        register=findViewById(R.id.card_reg);

        login.setOnClickListener(this);
        register.setOnClickListener(this);

        charSequences = new CharSequence[]{
                "1. As a condition of use, I promise not to use the services for any purpose that is unlawful.",
                "2. To abuse, harass, threaten, intimidate or impersonate anyone.",
                "3 To create or transmit unwanted spam to any person of URL",
                "4 To create multiple accounts for the purpose of fraud",
                "5 To use this application to defile others",
        };

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.action_more)
        {
            Intent more_apps = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps?hl=en"));
            startActivity(more_apps);
            return true;
        }
        if (id==R.id.action_exit)
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Do you want to exit the application?");
            builder.setCancelable(true);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
            return true;
        }
        if (id==R.id.action_dev)
        {
            Intent help = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/samuelngala"));
            startActivity(help);
            return true;
        }
        if (id==R.id.action_policy) {
            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Terms and Conditions");
            builder.setItems(charSequences, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //No action
                }
            });
            builder.setCancelable(true);

            builder.setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setNegativeButton("Disagree", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.card_log:
                {
                    intent=new Intent(this, Login.class);
                    startActivity(intent);
                    break;
                }
            case R.id.card_reg:
            {
                intent=new Intent(this, Register.class);
                startActivity(intent);
                break;
            }
        }
    }
}
