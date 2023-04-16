package com.example.doan;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CalendarAdapter extends BaseAdapter {
    private Context context;
    private int month;
    private int year;
    private List<Date> dates;
    private Map<Date, List<CalendarEvent>> events;
    private LayoutInflater inflater;

    public CalendarAdapter(Context context, int month, int year) {
        this.context = context;
        this.month=month;
        this.year=year;
        inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return dates.size();
    }

    public Object getItem(int position) {
        return dates.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Date date = dates.get(position);
        int day = date.getDay();
        int month = date.getMonth();
        int year = date.getYear() + 1900;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.calendar, parent, false);
        }

        TextView dayView = (TextView) convertView.findViewById(R.id.calendar_day_textview);
        dayView.setText(String.valueOf(day));

        if (month != this.month || year != this.year) {
            dayView.setTextColor(Color.GRAY);
        } else {
            dayView.setTextColor(Color.BLACK);
        }

        if (date.equals(Calendar.getInstance().getTime())) {
            dayView.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            dayView.setTypeface(Typeface.DEFAULT);
        }

        /*List<CalendarEvent> eventsList = events.get(date);
        if (eventsList != null) {
            for (CalendarEvent event : eventsList) {
                if (event.getColor() != null) {
                    dayView.setBackgroundColor(Color.parseColor(event.getColor()));
                }
            }
        }*/

        return convertView;
    }

    private List<Date> getDates(Calendar month) {
        List<Date> dates = new ArrayList<>();
        Calendar calendar = (Calendar) month.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        while (dates.size() < 42) {
            dates.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return dates;
    }
}
