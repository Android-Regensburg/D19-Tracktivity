package de.ur.mi.android.demos.tracktivity.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.time.LocalDate;
import java.util.List;

import de.ur.mi.android.demos.tracktivity.tracking.DailyGoal;

@Dao
public interface DailyGoalDOA {

    @Insert
    void insertDailyGoal(DailyGoal day);

    @Update
    void updateDailyGoal(DailyGoal day);

    @Query("SELECT * from goals WHERE date= :date")
    DailyGoal getGoalForDate(LocalDate date);

    @Query("SELECT * from goals WHERE date>= :date")
    List<DailyGoal> getGoalsForDaysAfterOrAt(LocalDate date);

}
