package com.example.android.me_cal.Adapters;

/**
 * Created by steffichoi on 8/29/17.
 */

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.me_cal.Fragments.TaskDetailFragment;
import com.example.android.me_cal.Helper.HelperFunctions;
import com.example.android.me_cal.data.AddTaskContract;
import com.example.android.me_cal.R;

public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.MyViewHolder> {

    private Context mContext;
    private Cursor mCursor;
    private String[] mDataset;

    private String taskName;
    private String taskTime;

    public TodayAdapter(Context context, Cursor cursor, String[] myDataset) {
        this.mContext = context;
        mCursor = cursor;
        mDataset = myDataset;
    }

    @Override
    public TodayAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.today_card_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if (!mCursor.moveToPosition(position)) {
            holder.mTaskTimeTv.setText("NO EVENTS FOUND!");
            return;
        }

        taskName = mCursor.getString(
                mCursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_NAME));
        taskTime = mCursor.getString(
                mCursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_TIME));

        holder.mTaskNameTv.setText(taskName);
        holder.mTaskTimeTv.setText(taskTime);

        long id = mCursor.getLong(mCursor.getColumnIndex(AddTaskContract.AddTaskEntry._ID));
        holder.itemView.setTag(id);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentValue = mDataset[position];
                Log.d("CardView", "CardView Clicked: " + currentValue);

                final HelperFunctions helperFunctions = new HelperFunctions(mContext);

                Fragment fragment = new TaskDetailFragment();
                Bundle bundle = new Bundle();

                bundle.putString("task_name", taskName);
                bundle.putString("task_time", taskTime);
                fragment.setArguments(bundle);

                helperFunctions.switchMainContentFragment(fragment, mContext);
            }
        });
    }

    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        // TODO: get rid of +1(for testing purposes)
        return mCursor.getCount()+1;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView mTaskTimeTv;
        public TextView mTaskNameTv;
        public MyViewHolder(View v) {
            super(v);

            mCardView = (CardView) v.findViewById(R.id.card_view);
            mTaskTimeTv = (TextView) v.findViewById(R.id.task_time_tv);
            mTaskNameTv = (TextView) v.findViewById(R.id.task_name_tv);
        }
    }
}
