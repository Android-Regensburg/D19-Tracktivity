package de.ur.mi.android.demos.tracktivity.tracking;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;

public class Day {

    public final LocalDate date;
    public final GoalState state;

    public Day(LocalDate date, GoalState state) {
        this.date = date;
        this.state = state;
    }

    static Day forDayInCurrentMonth(int dayInCurrentMonth) {
        int currentYear =  LocalDate.now().getYear();
        int currentMonth =  LocalDate.now().getMonth().getValue();
        LocalDate day = LocalDate.of(currentYear, currentMonth, dayInCurrentMonth);
        return new Day(day, GoalState.UNKNOWN);
    }

    public enum GoalState {
        UNKNOWN,
        REACHED,
        MISSED;

        public GoalState next() {
            int nextOrdinal  = this.ordinal() < GoalState.values().length - 1 ? this.ordinal() + 1 : 0;
            return GoalState.values()[nextOrdinal];
        }
    }
}
