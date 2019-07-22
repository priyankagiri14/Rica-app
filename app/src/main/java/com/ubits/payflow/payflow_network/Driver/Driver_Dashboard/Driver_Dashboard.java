package com.ubits.payflow.payflow_network.Driver.Driver_Dashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.ubits.payflow.payflow_network.Agent_Login.Agent_Login_Activity;
import com.ubits.payflow.payflow_network.General.Dashboard;
import com.ubits.payflow.payflow_network.Login.Login_Activity;
import com.ubits.payflow.payflow_network.Navigation_main.Navigation_Main;
import com.ubits.payflow.payflow_network.R;

public class Driver_Dashboard extends AppCompatActivity {

    private Toolbar toolbar;
    private Handler mHandler;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    public static int navItemIndex = 0;
    public boolean shouldLoadHomeFragOnBackPress = true;
    public static String TAG_DASHBOARD = "Customer Dashboard";
    public static String CURRENT_TAG = TAG_DASHBOARD;

    @SuppressLint("ResourceAsColor")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_dashboard);
        mHandler=new Handler();
        toolbar = findViewById(R.id.driver_toolbar);
        drawerLayout = findViewById(R.id.driver_drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        setSupportActionBar(toolbar);
        setNavigationView();

    }
    @Override
public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.logout_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.logout:
                Intent i=new Intent(this, Navigation_Main.class);
                startActivity(i);
                finish();
        }
        return true;
    }
    private void setNavigationView() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.Stocks:
                        navItemIndex=0;
                        CURRENT_TAG=TAG_DASHBOARD;
                        break;

                    case R.id.Agent:
                        navItemIndex=1;
                        CURRENT_TAG=TAG_DASHBOARD;
                        break;

                    /*case 2:
                        navItemIndex=2;
                        startActivity(new Intent(Driver_Dashboard.this,Driver_Dashboard.class));
                        finish();*/

                    case R.id.signout:
                        startActivity(new Intent(Driver_Dashboard.this, Agent_Login_Activity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                        return true;
                    default:
                        navItemIndex=0;
                }
                loadDashboardFragment();
                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
    private void setToolbarTitle(){
        getSupportActionBar().setTitle("Toolbar");
    }
    private void selectNavMenu(){
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }
    private void loadDashboardFragment(){
        selectNavMenu();
        setToolbarTitle();

        if(getSupportFragmentManager().findFragmentById(navItemIndex)!=null){
            drawerLayout.closeDrawers();
            return;
        }

        //Runnable runnable=new Runnable() {
//            @Override
//            public void run() {
//                Fragment fragment=getDashboardFragment();
//                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slide_from_left);
//                fragmentTransaction.replace(R.id.frame1,fragment,CURRENT_TAG);
//                fragmentTransaction.commit();
//            }
//        };
//
//        if(runnable!=null){
//            mHandler.post(runnable);
//        }
        drawerLayout.closeDrawers();
        invalidateOptionsMenu();
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
            return ;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            /*if (navItemIndex != 0) {*/
                startActivity(new Intent(Driver_Dashboard.this,Driver_Dashboard.class));
                CURRENT_TAG = TAG_DASHBOARD;
                loadDashboardFragment();
                return;
           // }
        }

        super.onBackPressed();
    }

}



