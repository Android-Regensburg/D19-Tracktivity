package de.ur.mi.android.demos.tracktivity.tracking;

import java.time.YearMonth;
import java.util.Arrays;

public class DayTracker {

    private DayTrackerListener listener;
    private Day[] trackedDaysInCurrentMonth;

    public DayTracker(DayTrackerListener listener) {
        this.listener = listener;
        initTracking();
    }

    private void initTracking() {
        int numberOfDaysInCurrentMonth = YearMonth.now().lengthOfMonth();
        trackedDaysInCurrentMonth = new Day[numberOfDaysInCurrentMonth];
        for (int i = 1; i <= trackedDaysInCurrentMonth.length; i++) {
            trackedDaysInCurrentMonth[i-1] = Day.forDayInCurrentMonth(i);
        }
    }

    public void switchStateForDay(Day day) {
        for (int i = 0; i < trackedDaysInCurrentMonth.length; i++) {
            Day dayToCheck = trackedDaysInCurrentMonth[i];
            if(dayToCheck.date.equals(day.date)) {
                trackedDaysInCurrentMonth[i] = new Day(dayToCheck.date, dayToCheck.state.next());
                listener.onTrackerUpdated();
                return;
            }
        }
    }

    public Day[] getTrackedDaysInCurrentMonth() {
        return Arrays.copyOf(trackedDaysInCurrentMonth, trackedDaysInCurrentMonth.length);
    }

    public interface DayTrackerListener {
        void onTrackerUpdated();
    }
}
