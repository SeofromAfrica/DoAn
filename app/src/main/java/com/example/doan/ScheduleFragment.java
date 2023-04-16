package com.example.doan;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;


public class ScheduleFragment extends Fragment {
    private RecyclerView recyclerView;
    private ScheduleAdapter scheduleAdapter;
    private FloatingActionButton fabAdd;
    private List<Schedule> scheduleList = new ArrayList<>();
    private ScheduleDBHelper dbHelper;
    private long id=-1;

    public ScheduleFragment() {
        // Required empty public constructor
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        recyclerView = view.findViewById(R.id.rv_schedule);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(scheduleAdapter);
        dbHelper = new ScheduleDBHelper(getActivity()) {
            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };

        FloatingActionButton fab = view.findViewById(R.id.fab_add_schedule);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddScheduleDialog();
            }
        });

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecyclerView();
        loadSchedules();

        scheduleAdapter.setOnScheduleClickListener(new ScheduleAdapter.OnScheduleClickListener() {
            @Override
            public void onScheduleClick(int position, Schedule schedule) {
                Schedule schedule1 = scheduleList.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Edit Schedule");
                View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_schedule, null);
                builder.setView(view);

                EditText titleEditText = view.findViewById(R.id.edt_title);
                EditText timeEditText = view.findViewById(R.id.edt_time);
                titleEditText.setText(schedule.getTitle());
                timeEditText.setText(schedule.getTime());

                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = titleEditText.getText().toString();
                        String time = timeEditText.getText().toString();

                        if (TextUtils.isEmpty(title)) {
                            titleEditText.setError("Title is required!");
                            return;
                        }

                        if (TextUtils.isEmpty(time)) {
                            timeEditText.setError("Time is required!");
                            return;
                        }

                        schedule1.setTitle(title);
                        schedule1.setTime(time);
                        dbHelper.updateSchedule(schedule1);
                        loadSchedules();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        scheduleAdapter.setDeleteClickListener(new ScheduleAdapter.OnScheduleDeleteClickListener() {
            @Override
            public void onScheduleDeleteClick(int position) {
                Schedule schedule = scheduleList.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete Schedule");
                builder.setMessage("Are you sure you want to delete this schedule?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        long deletedRows = dbHelper.deleteSchedule(schedule.getId());
                        if (deletedRows > 0){
                            scheduleList.remove(position);
                            scheduleAdapter.notifyItemRemoved(position);
                        }else {
                            Toast.makeText(getContext(), "Fail to delete", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
          builder.show();
            }
        });
    }
    private void setRecyclerView() {
        scheduleAdapter = new ScheduleAdapter(scheduleList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(scheduleAdapter);
    }
    private void loadSchedules() {
        scheduleList.clear();
        scheduleList.addAll(dbHelper.getAllSchedules());
        scheduleAdapter.notifyDataSetChanged();
    }
    private void showAddScheduleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Event");

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_schedule, null);
        builder.setView(view);

        final EditText titleEditText = view.findViewById(R.id.edt_title);
        final EditText timeEditText = view.findViewById(R.id.edt_time);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String title = titleEditText.getText().toString();
                String time = timeEditText.getText().toString();

                if (TextUtils.isEmpty(title)) {
                    titleEditText.setError("Title is required!");
                    return;
                }

                if (TextUtils.isEmpty(time)) {
                    timeEditText.setError("Time is required!");
                    return;
                }

                dbHelper.addSchedule(new Schedule(title, time));
                loadSchedules();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}