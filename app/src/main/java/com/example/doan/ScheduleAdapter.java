package com.example.doan;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    private List<Schedule> schedules;
    private LayoutInflater inflater;
    private  OnScheduleDeleteClickListener deleteClickListener;
    private OnScheduleClickListener scheduleClickListener;
    public ScheduleAdapter(List<Schedule> schedules) {
        this.schedules=schedules;
    }
    public void setDeleteClickListener(OnScheduleDeleteClickListener listener) {
        deleteClickListener = listener;
    }
    public void setOnScheduleClickListener(OnScheduleClickListener listener) {
        scheduleClickListener = listener;
    }
    public interface OnScheduleDeleteClickListener {
        void onScheduleDeleteClick(int position);
    }
    public interface OnScheduleClickListener {
        void onScheduleClick(int position, Schedule schedule);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView time;
        ImageButton deleteBtn;


        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            time = view.findViewById(R.id.time);
            deleteBtn = view.findViewById(R.id.deleteBtn);
        }
    }
    public ScheduleAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        schedules = new ArrayList<>();
    }


    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ScheduleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.from(viewGroup.getContext()).inflate(R.layout.schedule,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleAdapter.ViewHolder viewHolder, int position) {
        Schedule schedule = schedules.get(position);
        viewHolder.title.setText(schedule.getTitle());
        viewHolder.time.setText(schedule.getTime());

        viewHolder.itemView.setOnClickListener(view -> {
            if (scheduleClickListener !=null) {
                scheduleClickListener.onScheduleClick(position, schedule);
            }
        });

        viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deleteClickListener != null){
                    int clickPosition = viewHolder.getAdapterPosition();
                    deleteClickListener.onScheduleDeleteClick(clickPosition);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }
    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
        notifyDataSetChanged();
    }


    public void updateSchedule(Schedule schedule, int position) {
        schedules.set(position, schedule);
        notifyDataSetChanged();
    }
}
