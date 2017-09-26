package com.example.android.me_cal;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.icu.text.DateFormatSymbols;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.android.me_cal.Fragments.CustomCalendarFragment;
import com.example.android.me_cal.Fragments.ShoppingFragment;
import com.example.android.me_cal.Fragments.TaskDetailFragment;
import com.example.android.me_cal.Fragments.ToDoFragment;
import com.example.android.me_cal.Fragments.TodayFragment;
import com.example.android.me_cal.Fragments.TodaySideBarFragment;
import com.example.android.me_cal.Helper.HelperFunctions;
import com.example.android.me_cal.NotificationUtil.*;
import com.example.android.me_cal.data.AddTaskContract;
import com.example.android.me_cal.data.AddTaskDbHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    HelperFunctions helperFunctions = new HelperFunctions(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        boolean detail = intent.getBooleanExtra("to_detail", false);
        boolean today = intent.getBooleanExtra("to_today", false);

        //Generate the calendar view as a fragment so it will be interchangable with
        //other fragments associated with the navigation actions
        if (detail) {
            Fragment taskDetailFragment = new TaskDetailFragment();
            Bundle bundle = new Bundle();

            bundle.putString("task_name", intent.getStringExtra("task_name"));
            bundle.putLong("task_time", intent.getLongExtra("task_time", -1));
            bundle.putLong("task_id", intent.getLongExtra("task_id", -1));
            taskDetailFragment.setArguments(bundle);

            helperFunctions.switchMainContentFragment(taskDetailFragment, this);

            AddTaskDbHelper dbHelper = new AddTaskDbHelper(this);
            Cursor cursor = dbHelper.getTask(intent.getLongExtra("task_time", -1));
            long startTimeFromDb = cursor.getLong(cursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_TIME_START));
            String[] start = helperFunctions.getDateTime(startTimeFromDb).split("\\s+");

            Fragment todaySideBarFragment = new TodaySideBarFragment();
            bundle.putInt("date_from_cal", Integer.parseInt(start[1]));
            bundle.putInt("month_from_cal", helperFunctions.getMonthInt(start[2]));
            bundle.putInt("year_from_cal", Integer.parseInt(start[3]));

            todaySideBarFragment.setArguments(bundle);
            helperFunctions.switchSideContentFragment(todaySideBarFragment, this);

        } else if (today) {
          Fragment todayFragment = new TodayFragment();
            Bundle bundle = new Bundle();

            bundle.putInt("date_from_cal", intent.getIntExtra("date_from_cal", 0));
            bundle.putInt("month_from_cal", intent.getIntExtra("month_from_cal", 0));
            bundle.putInt("year_from_cal", intent.getIntExtra("year_from_cal", 0));

            todayFragment.setArguments(bundle);
            helperFunctions.switchMainContentFragment(todayFragment, this);
            helperFunctions.switchSideContentFragment(new ToDoFragment(), this);

        } else {
            helperFunctions.switchMainContentFragment(new CustomCalendarFragment(), this);
            helperFunctions.switchSideContentFragment(new TodaySideBarFragment(), this);
        }


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
            helperFunctions.switchMainContentFragment(new TodayFragment(), this);
            helperFunctions.switchSideContentFragment(new ToDoFragment(), this);
        } else if (id == R.id.nav_week) {

            final Intent intent = new Intent(this, WeekActivity.class);
            Fragment currentFrag = getFragmentManager().findFragmentById(R.id.main_content_frame);
            int[] date = helperFunctions.getCurrDateIntArray();
            if (currentFrag instanceof CustomCalendarFragment) {
                // get the date and add it to intent extra
                date = ((CustomCalendarFragment) currentFrag).getDatePicked();

                Toast.makeText(this, "curr frag is customcal!!, " + date[0] + " " + date[1] + " " + date[2], Toast.LENGTH_LONG).show();

            } else if (currentFrag instanceof TodayFragment) {
                date = ((TodayFragment) currentFrag).getDatePicked();
                Toast.makeText(this, "curr frag is TODAY!!" + date, Toast.LENGTH_SHORT).show();
                // get the date and add it to intent extra

            }
            intent.putExtra("date_picked", date);
            startActivity(intent);

        } else if (id == R.id.nav_month) {
            helperFunctions.switchMainContentFragment(new CustomCalendarFragment(), this);
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
}
