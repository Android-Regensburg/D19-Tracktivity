package de.ur.mi.android.demos.tracktivity.tracking;

import java.time.YearMonth;
import java.util.Arrays;

public class GoalTracker {

    private final GoalTrackerListener listener;
    private DailyGoal[] trackedGoalsInThisMonth;

    public GoalTracker(GoalTrackerListener listener) {
        this.listener = listener;
        initTracking();
    }


    private void initTracking() {
        int numberOfDaysInCurrentMonth = YearMonth.now().lengthOfMonth();
        trackedGoalsInThisMonth = new DailyGoal[numberOfDaysInCurrentMonth];
        for (int i = 0; i < trackedGoalsInThisMonth.length; i++) {
            trackedGoalsInThisMonth[i] = DailyGoal.forDayInCurrentMonth(i + 1);
        }
    }

    public void switchGoalStateFor(DailyGoal day) {
        for (int i = 0; i < trackedGoalsInThisMonth.length; i++) {
            DailyGoal dayToCheck = trackedGoalsInThisMonth[i];
            if (dayToCheck.date.equals(day.date)) {
                trackedGoalsInThisMonth[i] = new DailyGoal(dayToCheck.date, dayToCheck.state.next());
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
