package de.ur.mi.android.demos.tracktivity.tracking;

import android.content.Context;

import java.time.YearMonth;
import java.util.Arrays;

import de.ur.mi.android.demos.tracktivity.db.DayDatabaseHelper;

public class GoalTracker {

    private final GoalTrackerListener listener;
    private DayDatabaseHelper db;
    private DailyGoal[] trackedGoalsInThisMonth;

    public GoalTracker(Context context, GoalTrackerListener listener) {
        this.listener = listener;
        initDatabase(context);
        initTracking();
    }

    private void initDatabase(Context context) {
        db = new DayDatabaseHelper(context);
    }

    private void initTracking() {
        int numberOfDaysInCurrentMonth = YearMonth.now().lengthOfMonth();
        trackedGoalsInThisMonth = new DailyGoal[numberOfDaysInCurrentMonth];
        for (int i = 0; i < trackedGoalsInThisMonth.length; i++) {
            DailyGoal possibleDayForTracking = DailyGoal.forDayInCurrentMonth(i + 1);
            DailyGoal existingDayEntryInDatabase = db.getDayForDate(possibleDayForTracking.date);
            if (existingDayEntryInDatabase == null) {
                db.addOrUpdateDay(possibleDayForTracking);
                trackedGoalsInThisMonth[i] = possibleDayForTracking;
            } else {
                trackedGoalsInThisMonth[i] = existingDayEntryInDatabase;
            }
        }
    }

    public void switchGoalStateFor(DailyGoal day) {
        for (int i = 0; i < trackedGoalsInThisMonth.length; i++) {
            DailyGoal dayToCheck = trackedGoalsInThisMonth[i];
            if (dayToCheck.date.equals(day.date)) {
                trackedGoalsInThisMonth[i] = new DailyGoal(dayToCheck.date, dayToCheck.state.next());
                db.addOrUpdateDay(trackedGoalsInThisMonth[i]);
                listener.onTrackerUpdated();
                return;
            }
        }
    }

    public DailyGoal[] getGoalStatesForThisMonth() {
        return Arrays.copyOf(trackedGoalsInThisMonth, trackedGoalsInThisMonth.length);
    }

    public interface GoalTrackerListener {
        void onTrackerUpdated();
    }
}
