package de.ur.mi.android.demos.tracktivity.db;

import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import de.ur.mi.android.demos.tracktivity.tracking.DailyGoal;

public class DailyGoalAttributeTypeConverter {

    @TypeConverter
    public static LocalDate toDate(Long epochMilli) {
        return Instant.ofEpochMilli(epochMilli).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @TypeConverter
    public static Long toDateTimestamp(LocalDate date) {
        return date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    @TypeConverter
    public static DailyGoal.GoalState toGoalState(Integer goalStateInteger) {
        return DailyGoal.GoalState.values()[goalStateInteger];
    }

    @TypeConverter
    public static Integer toGoalStateInteger(DailyGoal.GoalState goalState) {
        return goalState.ordinal();
    }
}
