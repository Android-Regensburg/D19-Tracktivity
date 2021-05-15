package de.ur.mi.android.demos.tracktivity.tracking;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "goals")
public class DailyGoal {

    @PrimaryKey
    @NonNull
    public final LocalDate date;
    @ColumnInfo(name = "state")
    public final GoalState state;

    public DailyGoal(LocalDate date, GoalState state) {
        this.date = date;
        this.state = state;
    }

    static DailyGoal forDayInCurrentMonth(int dayInCurrentMonth) {
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonth().getValue();
        LocalDate day = LocalDate.of(currentYear, currentMonth, dayInCurrentMonth);
        return new DailyGoal(day, GoalState.UNKNOWN);
    }

    public enum GoalState {
        UNKNOWN,
        REACHED,
        MISSED;

        public GoalState next() {
            int nextOrdinal = this.ordinal() < GoalState.values().length - 1 ? this.ordinal() + 1 : 0;
            return GoalState.values()[nextOrdinal];
        }
    }
}
