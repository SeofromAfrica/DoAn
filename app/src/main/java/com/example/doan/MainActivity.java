package com.example.doan;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.Date;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ScheduleFragment scheduleFragment = new ScheduleFragment();
    CalendarFragment calendarFragment = new CalendarFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.btmNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,scheduleFragment).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.mnuSchedule:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,scheduleFragment).commit();
                        return true;
                    case R.id.mnuCalendar:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, calendarFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}