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
import android.widget.Toast;

import com.example.android.me_cal.Fragments.TaskDetailFragment;
import com.example.android.me_cal.Fragments.TodaySideBarFragment;
import com.example.android.me_cal.Helper.HelperFunctions;
import com.example.android.me_cal.data.AddTaskContract;
import com.example.android.me_cal.R;

public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.MyViewHolder> {

    private Context mContext;
    private Cursor mCursor;
    private String[] mDataset;


    public TodayAdapter(Context context, Cursor cursor, String[] myDataset) {
        mContext = context;
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
            return;
        }

        final String taskName = mCursor.getString(
                mCursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_NAME));
        final long taskTime = mCursor.getLong(
                mCursor.getColumnIndex(AddTaskContract.AddTaskEntry.COLUMN_TASK_TIME_START));
        final long id = mCursor.getLong(mCursor.getColumnIndex(AddTaskContract.AddTaskEntry._ID));

        final HelperFunctions helperFunctions = new HelperFunctions(mContext);
        String time = helperFunctions.getTime(taskTime);

        holder.mTaskNameTv.setText(taskName);
        holder.mTaskTimeTv.setText(time);

        holder.itemView.setTag(id);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentValue = mDataset[position];
                Log.d("CardView", "CardView Clicked: " + currentValue);

                Fragment tdFragment = new TaskDetailFragment();
                Bundle bundle = new Bundle();

                bundle.putString("task_name", taskName);
                bundle.putLong("task_time", taskTime);
                bundle.putLong("task_id", id);
                tdFragment.setArguments(bundle);

                helperFunctions.switchMainContentFragment(tdFragment, mContext);

                Fragment sbFragment = new TodaySideBarFragment();

                String[] dateString = helperFunctions.getDateTime(taskTime).split("\\s+");
                bundle.putInt("date_from_cal", Integer.parseInt(dateString[1]));
                bundle.putInt("month_from_cal", helperFunctions.getMonthInt(dateString[2]));
                bundle.putInt("year_from_cal", Integer.parseInt(dateString[3]));

                sbFragment.setArguments(bundle);

                helperFunctions.switchSideContentFragment(sbFragment, mContext);
            }
        });
    }

    public void swapCursor(Cursor newCursor) {

        if (mCursor != null) mCursor.close();

        mCursor = newCursor;
        if (newCursor != null) this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
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
