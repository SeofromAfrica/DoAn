package com.example.doan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {

    private TextView monthYearTextView;
    private ImageButton prevMonthButton, nextMonthButton;
    private GridView calendarGridView;
    private CalendarAdapter calendarAdapter;
    private int month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_calendar);

        // Initialize views
        monthYearTextView = findViewById(R.id.current_month_text);
        prevMonthButton = findViewById(R.id.previous_month_button);
        nextMonthButton = findViewById(R.id.next_month_button);
        calendarGridView = findViewById(R.id.calendar_grid);

        // Set click listeners for previous and next month buttons
        prevMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                month--;
                if (month < 0) {
                    month = 11;
                    year--;
                }
                updateCalendar();
            }

            private void updateCalendar() {
                // Lấy ngày đầu tiên của tháng hiện tại
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH, 1);

                // Lấy ngày cuối cùng của tháng hiện tại
                int lastDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                cal.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);

                calendarAdapter.notifyDataSetChanged();
            }
        });



        /*nextMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                month++;
                if (month > 11) {
                    month = 0;
                    year++;
                }
                updateCalendar();
            }
        });*/

        // Get current month and year
        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        // Set month and year text
        updateMonthYearText();

        // Initialize calendar adapter
        calendarAdapter = new CalendarAdapter(this, month, year);
        calendarGridView.setAdapter(calendarAdapter);
    }

    private void updateMonthYearText() {

    }
}
