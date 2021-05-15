package de.ur.mi.android.demos.tracktivity.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import de.ur.mi.android.demos.tracktivity.tracking.DailyGoal;

@Database(entities = {DailyGoal.class}, version = 1)
@TypeConverters({DailyGoalAttributeTypeConverter.class})

public abstract class DayDatabase extends RoomDatabase {
    public abstract DailyGoalDOA daysDAO();
}
