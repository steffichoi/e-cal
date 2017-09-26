package com.example.android.me_cal;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.DateFormatSymbols;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import com.example.android.me_cal.Fragments.ShoppingFragment;
import com.example.android.me_cal.Fragments.ToDoFragment;
import com.example.android.me_cal.Fragments.TodayFragment;
import com.example.android.me_cal.Fragments.TodaySideBarFragment;
import com.example.android.me_cal.Fragments.WeekFragment;
import com.example.android.me_cal.Helper.HelperFunctions;

import java.util.Calendar;
import java.util.Date;

public class WeekActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    CompactCalendarView compactCalendarView;
    private boolean expanded = false;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private TextView monthTv;
    private int[] dateArray;

    HelperFunctions helperFunctions = new HelperFunctions(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);

        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_week_layout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_week);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        dateArray = intent.getIntArrayExtra("date_picked");

        monthTv = (TextView) findViewById(R.id.toolbar_month_tv);
        monthTv.setText(new DateFormatSymbols().getMonths()[dateArray[1]]);

        compactCalendarView = (CompactCalendarView) findViewById(R.id.compact_calendar_view);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateClicked);
                String[] time = helperFunctions.getDateTime(cal.getTimeInMillis()).split("\\s+");

                dateArray[0] = Integer.parseInt(time[1]);
                dateArray[1] = helperFunctions.getMonthInt(time[2]);
                dateArray[2] = Integer.parseInt(time[3]);

                Intent newWeekIntent = new Intent(getApplicationContext(), WeekActivity.class);
                newWeekIntent.putExtra("date_picked", dateArray);
                startActivity(newWeekIntent);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                monthTv = (TextView) findViewById(R.id.toolbar_month_tv);
                Calendar cal = Calendar.getInstance();
                cal.setTime(firstDayOfNewMonth);
                String[] time = helperFunctions.getDateTime(cal.getTimeInMillis()).split("\\s+");
                monthTv.setText(time[2]);
            }
        });

        final ImageView toolbar_cal = (ImageView) findViewById(R.id.toolbar_calendar);
        toolbar_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expanded = !expanded;
                appBarLayout.setExpanded(expanded, true);
            }
        });

        Fragment[] weekFrags = getWeekFragments(dateArray[0], dateArray[1], dateArray[2]);
        int[] contentIds = {R.id.d1_content_frame, R.id.d2_content_frame, R.id.d3_content_frame,
                R.id.d4_content_frame, R.id.d5_content_frame, R.id.d6_content_frame, R.id.d7_content_frame};

        for (int i=0; i<7; i++) {
            helperFunctions.switchFragment(contentIds[i], weekFrags[i], this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_week_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_week_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_week_layout);
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
            helperFunctions.switchMainContentFragment(new WeekFragment(), this);
        } else if (id == R.id.nav_month) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_to_do) {
            helperFunctions.switchMainContentFragment(new ToDoFragment(), this);
        } else if (id == R.id.nav_shopping) {
            helperFunctions.switchMainContentFragment(new ShoppingFragment(), this);
        } else if (id == R.id.filter_fix_schedule) {

        } else if (id == R.id.filter_events) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_week_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private Fragment[] getWeekFragments(int date, int month, int year) {
        Fragment[] weekFrags = new Fragment[7];

        for (int i=0; i<7; i++) {
            Fragment weekDayFrag = new WeekFragment();
            Bundle bundle = new Bundle();

            bundle.putInt("date_from_cal", date);
            bundle.putInt("month_from_cal", month);
            bundle.putInt("year_from_cal", year);

            weekDayFrag.setArguments(bundle);
            weekFrags[i] = weekDayFrag;
            date++;
        }
        return weekFrags;
    }

    private DatePickerDialog.OnDateSetListener setDateListener() {
        return new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String monthString = new DateFormatSymbols().getMonths()[month];

                String dayOfWeek = DateFormat.format("EE", new Date(year, month, dayOfMonth - 1)).toString();

                Fragment sbFragment = new TodaySideBarFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("date_from_cal", dayOfMonth);
                bundle.putInt("month_from_cal", month);
                bundle.putInt("year_from_cal", year);

                sbFragment.setArguments(bundle);
//                helperFunctions.switchSideContentFragment(sbFragment, getActivity());
            }
        };
    }
}
