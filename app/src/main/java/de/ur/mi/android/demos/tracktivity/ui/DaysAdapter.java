package de.ur.mi.android.demos.tracktivity.ui;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;

import de.ur.mi.android.demos.tracktivity.R;
import de.ur.mi.android.demos.tracktivity.tracking.Day;
import de.ur.mi.android.demos.tracktivity.ui.viewholder.DayViewHolder;

public class DaysAdapter extends RecyclerView.Adapter<DayViewHolder> implements DayViewHolder.DayViewHolderListener {

    private final DaysAdapterListener listener;
    private Day[] trackedDays;

    public DaysAdapter(DaysAdapterListener listener) {
        this.listener = listener;
        trackedDays = new Day[0];
    }

    public void setTrackedDays(Day[] trackedDays) {
        this.trackedDays = trackedDays;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Day day = trackedDays[position];
        return day.state.ordinal();
    }

    @Override
    public int getItemCount() {
        return trackedDays.length;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DayViewHolder vh = createViewHolderForType(parent, viewType);
        return vh;
    }

    private DayViewHolder createViewHolderForType(ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.view_day, parent, false);
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        layoutParams.width = (int) ((parent.getWidth() / 7) * 0.7);
        layoutParams.height = layoutParams.width;
        v.setLayoutParams(layoutParams);
        return new DayViewHolder(v,this);
    }


    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        Day day = trackedDays[position];
        int selectedDay = position + 1;
        int currentDayInMonth = LocalDate.now().getDayOfMonth();
        int backgroundResourceForDayState = R.drawable.day_view_background_unknown;
        switch(day.state) {
            case REACHED:
                backgroundResourceForDayState = R.drawable.day_view_background_passed;
                break;
            case MISSED:
                backgroundResourceForDayState = R.drawable.day_view_background_missed;
                break;
            default:
                break;
        }
        if(selectedDay > LocalDate.now().getDayOfMonth()) {
            backgroundResourceForDayState = R.drawable.day_view_background_future;
        }
        holder.itemView.setBackgroundResource(backgroundResourceForDayState);
    }

    @Override
    public void onDayViewSelected(int viewPosition) {
        int selectedDay = viewPosition + 1;
        int currentDayInMonth = LocalDate.now().getDayOfMonth();
        if(selectedDay > LocalDate.now().getDayOfMonth()) {
            return;
        }
        listener.onDaySelected(trackedDays[viewPosition]);
    }

    public interface DaysAdapterListener {
        void onDaySelected(Day day);
    }
}
