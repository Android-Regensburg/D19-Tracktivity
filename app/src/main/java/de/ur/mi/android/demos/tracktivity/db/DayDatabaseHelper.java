package de.ur.mi.android.demos.tracktivity.db;

import android.content.Context;

import androidx.room.Room;

import java.time.LocalDate;

import de.ur.mi.android.demos.tracktivity.tracking.DailyGoal;

public class DayDatabaseHelper {

    private static final String DATABASE_NAME = "tracktivity-db";
    private final Context context;
    private DayDatabase db;

    public DayDatabaseHelper(Context context) {
        this.context = context;
        initDatabase();
    }

    private void initDatabase() {
        db = Room.databaseBuilder(context, DayDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
    }

    public DailyGoal getDayForDate(LocalDate date) {
        return db.daysDAO().getGoalForDate(date);
    }

    public void addOrUpdateDay(DailyGoal day) {
        DailyGoal dayFromDatabase = db.daysDAO().getGoalForDate(day.date);
        if (dayFromDatabase == null) {
            db.daysDAO().insertDailyGoal(day);
        } else {
            db.daysDAO().updateDailyGoal(day);
        }
    }

    private LocalDate getFirstDayOfCurrentMonth() {
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonth().getValue();
        return LocalDate.of(currentYear, currentMonth, 1);
    }

}
