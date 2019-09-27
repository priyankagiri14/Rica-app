package com.tms.ontrack.mobile.Navigation_main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.tms.ontrack.mobile.AboutActivity;
import com.tms.ontrack.mobile.R;

public class Contact_Ontrack  extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolbar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_ontrack);
        toolbar=findViewById(R.id.toolbar);
        DrawerLayout drawer = findViewById(R.id.contact_drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            // Handle the camera action
            Intent i=new Intent(this, Navigation_Main.class);
            startActivity(i);
        } else if (id == R.id.nav_contacts) {

        } else if (id == R.id.nav_address) {
            Intent i2=new Intent(this, Address_Ontrack.class);
            startActivity(i2);
        }
        else if (id == R.id.aboutdrawer) {
            Intent i2=new Intent(this, AboutActivity.class);
            startActivity(i2);
        }

        DrawerLayout drawer = findViewById(R.id.contact_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
