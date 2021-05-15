package de.ur.mi.android.demos.tracktivity.ui.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DayViewHolder extends RecyclerView.ViewHolder {

    private final DayViewHolderListener listener;

    public DayViewHolder(@NonNull View itemView, DayViewHolderListener listener) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDayViewSelected(getAdapterPosition());
            }
        });
        this.listener = listener;
    }

    public interface DayViewHolderListener {

        void onDayViewSelected(int viewPosition);

    }

}
