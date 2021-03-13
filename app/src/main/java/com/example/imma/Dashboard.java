package com.example.imma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
public class Dashboard extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private CardView star, standard, nation, peoples, taifa, sports;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private CharSequence[] charSequences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        star=findViewById(R.id.star);
        standard=findViewById(R.id.standard);
        nation=findViewById(R.id.nation);
        peoples=findViewById(R.id.peoples);
        taifa=findViewById(R.id.taifa);
        sports=findViewById(R.id.sports);

        navigationView=findViewById(R.id.nav_view);
        drawerLayout=findViewById(R.id.drawer_layout);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle =  new ActionBarDrawerToggle(Dashboard.this, drawerLayout, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        star.setOnClickListener(this);
        standard.setOnClickListener(this);
        nation.setOnClickListener(this);
        peoples.setOnClickListener(this);
        taifa.setOnClickListener(this);
        sports.setOnClickListener(this);

        charSequences = new CharSequence[]{
                "* Launch the Application",
                "* Click login if already have an account",
                "* In case it is your first time, click register to create an account",
                "* When successful registered, you can login",
                "* Immediately after logged in, you will see a dashboard",
                "* The dashboard has the types of newspapers, choose and click the one you want to read"
        };
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.share)
        {
            Intent intent= new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "'Mobile magazine App which helps you in accessing all the types of newspapers!' https://play.google.com/store/apps/details?id=com.");
            startActivity(Intent.createChooser(intent, "Share via"));
        }
        if (id==R.id.help)
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(Dashboard.this);
            builder.setTitle("How To Use");
            builder.setItems(charSequences, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //No action
                }
            });
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
        {
            super.onBackPressed();
        }
    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.star:
            {
                intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.the-star.co.ke/"));
                startActivity(intent);
                break;
            }
            case R.id.standard:
            {
                intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.standardmedia.co.ke"));
                startActivity(intent);
                break;
            }
            case R.id.nation:
            {
                intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nation.co.ke"));
                startActivity(intent);
                break;
            }
            case R.id.peoples:
            {
                intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pd.co.ke/category/news/"));
                startActivity(intent);
                break;
            }
            case R.id.taifa:
            {
                intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.taifaleo.nation.co.ke"));
                startActivity(intent);
                break;
            }
            case R.id.sports:
            {
                intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mwanaspoti.co.tz"));
                startActivity(intent);
                break;
            }
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_account)
        {
            startActivity(new Intent(this, Register.class));
            return true;
        }
        else if(id==R.id.nav_manual)
        {
            startActivity(new Intent(this, Manual.class));
            return true;
        }
        else if(id==R.id.nav_home)
        {
            startActivity(new Intent(this, Dashboard.class));
            return true;
        }
        else if(id==R.id.nav_news)
        {
            startActivity(new Intent(this, Breaking_News.class));
            return true;
        }
        else if(id==R.id.nav_share)
        {
            Intent intent= new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "my app store application url");
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, "Share via"));
            return true;
        }
        else if(id==R.id.nav_logout)
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(Dashboard.this);
            builder.setMessage("Do you want to logout?");
            builder.setCancelable(true);

            builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
            return true;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
