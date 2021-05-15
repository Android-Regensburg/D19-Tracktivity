package de.ur.mi.android.demos.tracktivity.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;

import de.ur.mi.android.demos.tracktivity.R;
import de.ur.mi.android.demos.tracktivity.tracking.DailyGoal;
import de.ur.mi.android.demos.tracktivity.ui.viewholder.DailyGoalViewHolder;

public class DailyGoalsAdapter extends RecyclerView.Adapter<DailyGoalViewHolder> implements DailyGoalViewHolder.ViewHolderListener {

    private final AdapterListener listener;
    private DailyGoal[] trackedGoals;

    public DailyGoalsAdapter(AdapterListener listener) {
        this.listener = listener;
        trackedGoals = new DailyGoal[0];
    }

    public void setTrackedGoals(DailyGoal[] trackedGoals) {
        this.trackedGoals = trackedGoals;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        DailyGoal dailyGoal = trackedGoals[position];
        return dailyGoal.state.ordinal();
    }

    @Override
    public int getItemCount() {
        return trackedGoals.length;
    }

    @NonNull
    @Override
    public DailyGoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DailyGoalViewHolder vh = createViewHolderForType(parent, viewType);
        return vh;
    }

    private DailyGoalViewHolder createViewHolderForType(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_day, parent, false);
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        layoutParams.width = (int) ((parent.getWidth() / 7) * 0.7);
        layoutParams.height = layoutParams.width;
        v.setLayoutParams(layoutParams);
        return new DailyGoalViewHolder(v, this);
    }


    @Override
    public void onBindViewHolder(@NonNull DailyGoalViewHolder holder, int position) {
        DailyGoal day = trackedGoals[position];
        int selectedDay = position + 1;
        int backgroundResourceForDayState = R.drawable.day_view_background_unknown;
        switch (day.state) {
            case REACHED:
                backgroundResourceForDayState = R.drawable.day_view_background_passed;
                break;
            case MISSED:
                backgroundResourceForDayState = R.drawable.day_view_background_missed;
                break;
            default:
                break;
        }
        if (selectedDay > LocalDate.now().getDayOfMonth()) {
            backgroundResourceForDayState = R.drawable.day_view_background_future;
        }
        holder.itemView.setBackgroundResource(backgroundResourceForDayState);
    }

    @Override
    public void onDayViewSelected(int viewPosition) {
        int selectedDay = viewPosition + 1;
        if (selectedDay > LocalDate.now().getDayOfMonth()) {
            return;
        }
        listener.onDailyGoalSelected(trackedGoals[viewPosition]);
    }

    public interface AdapterListener {
        void onDailyGoalSelected(DailyGoal day);
    }
}
