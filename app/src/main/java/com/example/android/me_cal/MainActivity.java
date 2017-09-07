package com.example.android.me_cal;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.me_cal.Fragments.CustomCalendarView;
import com.example.android.me_cal.Fragments.ShoppingFragment;
import com.example.android.me_cal.Fragments.ToDoFragment;
import com.example.android.me_cal.Fragments.TodaySideBarFragment;
import com.example.android.me_cal.Fragments.WeekFragment;
import com.example.android.me_cal.Helper.HelperFunctions;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    HelperFunctions helperFunctions = new HelperFunctions(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Generate the calendar view as a fragment so it will be interchangable with
        //other fragments associated with the navigation actions
        helperFunctions.switchMainContentFragment(new CustomCalendarView(), this);
        helperFunctions.switchSideContentFragment(new TodaySideBarFragment(), this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_today) {
            helperFunctions.switchMainContentFragment(new TodaySideBarFragment(), this);
            helperFunctions.switchSideContentFragment(new ToDoFragment(), this);
        } else if (id == R.id.nav_week) {
            helperFunctions.switchMainContentFragment(new WeekFragment(), this);
        } else if (id == R.id.nav_month) {
            helperFunctions.switchMainContentFragment(new CustomCalendarView(), this);
            helperFunctions.switchSideContentFragment(new TodaySideBarFragment(), this);
        } else if (id == R.id.nav_to_do) {
            helperFunctions.switchMainContentFragment(new ToDoFragment(), this);
        } else if (id == R.id.nav_shopping) {
            helperFunctions.switchMainContentFragment(new ShoppingFragment(), this);
        } else if (id == R.id.filter_fix_schedule) {

        } else if (id == R.id.filter_events) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    private void switchMainContentFragment(Fragment fragment) {
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        fragmentTransaction.replace(R.id.main_content_frame, fragment);
//
//        fragmentTransaction.commit();
//    }
//
//    private void switchSideContentFragment(Fragment fragment) {
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        fragmentTransaction.replace(R.id.side_content_frame, fragment);
//
//        fragmentTransaction.commit();
//    }
}
