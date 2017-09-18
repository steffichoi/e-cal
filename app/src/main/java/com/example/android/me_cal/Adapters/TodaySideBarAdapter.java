package com.example.android.me_cal.Adapters;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.me_cal.Fragments.TaskDetailFragment;
import com.example.android.me_cal.Fragments.TodaySideBarFragment;
import com.example.android.me_cal.Helper.HelperFunctions;
import com.example.android.me_cal.data.AddTaskContract;
import com.example.android.me_cal.R;

/**
 * Created by steffichoi on 8/23/17.
 */

public class TodaySideBarAdapter extends
        RecyclerView.Adapter<TodaySideBarAdapter.ScheduleViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public TodaySideBarAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext)
                .inflate(R.layout.today_side_bar_item, parent, false);
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

                Fragment fragment = new TaskDetailFragment();
                Bundle bundle = new Bundle();

                bundle.putString("task_name", taskName);
                bundle.putLong("task_time", taskTime);
                bundle.putLong("task_id", id);
                fragment.setArguments(bundle);

                helperFunctions.switchMainContentFragment(fragment, mContext);
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

            taskTimeTextView = (TextView) itemView.findViewById(R.id.today_task_time_tv);
            taskNameTextView = (TextView) itemView.findViewById(R.id.today_task_name_tv);
        }
    }
}
