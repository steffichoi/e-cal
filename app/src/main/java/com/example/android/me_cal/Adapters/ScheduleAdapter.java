package com.example.android.me_cal.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.me_cal.data.AddTaskContract;
import com.example.android.me_cal.R;

/**
 * Created by steffichoi on 8/23/17.
 */

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private Context mContext;
    private Cursor mCursor;



    public ScheduleAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        mCursor = cursor;
    }


    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.today_side_bar_item, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        String taskName = mCursor.getString(mCursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_NAME));
        String taskTime = mCursor.getString(mCursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_TIME));

        holder.taskNameTextView.setText(taskName);
        holder.taskTimeTextView.setText(taskTime); //+ "   " + taskName);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {

        if (mCursor != null) {
            mCursor.close();
        }

        mCursor = newCursor;

        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
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
