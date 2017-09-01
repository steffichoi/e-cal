package com.example.android.me_cal.data;

import android.provider.BaseColumns;

/**
 * Created by steffichoi on 8/23/17.
 */

public class AddTaskContract {

    public static final class AddTaskEntry implements BaseColumns {

        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_TASK_NAME = "taskName";
        public static final String COLUMN_TASK_TIME = "taskTime";
        public static final String COLUMN_TASK_DATE = "taskDate";
        public static final String COLUMN_TASK_DURATION = "taskDuration";
        public static final String COLUMN_TASK_LOCATION = "taskLocation";
    }
}
