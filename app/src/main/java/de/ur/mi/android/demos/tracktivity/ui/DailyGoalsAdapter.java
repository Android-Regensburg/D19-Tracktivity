package de.ur.mi.android.demos.tracktivity.ui;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    @SuppressLint("NotifyDataSetChanged")
    public void setTrackedGoals(DailyGoal[] trackedGoals) {
        this.trackedGoals = trackedGoals;
        /*
         * Wir nutzen hier bewusst nicht die vorgeschlagenen Methoden zur partiellen Aktualisierung
         * des Views um ein möglicherweise falsch interpretierte Animation im UI zu verhindern. Die
         * beste Lösung wäre ein Diff des aktuellen und des neu übergebenen Arrays um dann die Listener
         * über die konkreten Veränderungen innerhalb der Liste zu informieren.
         */
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
        return createViewHolderForType(parent, viewType);
    }

    private DailyGoalViewHolder createViewHolderForType(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_day, parent, false);
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        int viewHolderSquareSize = (int) ((parent.getWidth() / 7) * 0.7);
        layoutParams.width = viewHolderSquareSize;
        layoutParams.height = viewHolderSquareSize;
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
        ((TextView) holder.itemView).setText(String.valueOf(selectedDay));
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
