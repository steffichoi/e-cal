package com.example.android.me_cal.Adapters;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.me_cal.Fragments.TaskDetailFragment;
import com.example.android.me_cal.Helper.HelperFunctions;
import com.example.android.me_cal.MainActivity;
import com.example.android.me_cal.R;
import com.example.android.me_cal.data.AddTaskContract;

/**
 * Created by steffichoi on 8/23/17.
 */

public class WeekAdapter extends
        RecyclerView.Adapter<WeekAdapter.ScheduleViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public WeekAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext)
                .inflate(R.layout.week_day_item, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder holder, int position) {

        final HelperFunctions helperFunctions = new HelperFunctions(mContext);
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        final String taskName = mCursor.getString(
                mCursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_NAME));
        final long taskTime = mCursor.getLong(
                mCursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_TIME_START));
        final long id = mCursor.getLong(mCursor.getColumnIndex(AddTaskContract.AddTaskEntry._ID));

        String time = helperFunctions.getTime(taskTime);

        holder.taskNameTextView.setText(taskName);
        holder.taskTimeTextView.setText(time);

        holder.taskNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, MainActivity.class);

                intent.putExtra("to_detail", true);
                intent.putExtra("task_name", taskName);
                intent.putExtra("task_time", taskTime);
                intent.putExtra("task_id", id);

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    class ScheduleViewHolder extends RecyclerView.ViewHolder {

        TextView taskNameTextView;
        TextView taskTimeTextView;

        public ScheduleViewHolder(View itemView) {
            super(itemView);

            taskTimeTextView = (TextView) itemView.findViewById(R.id.week_task_time_tv);
            taskNameTextView = (TextView) itemView.findViewById(R.id.week_task_name_tv);
        }
    }
}
