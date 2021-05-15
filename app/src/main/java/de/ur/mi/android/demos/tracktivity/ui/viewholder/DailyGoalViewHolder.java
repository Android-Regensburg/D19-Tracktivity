package de.ur.mi.android.demos.tracktivity.ui.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DailyGoalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final ViewHolderListener listener;

    public DailyGoalViewHolder(@NonNull View itemView, ViewHolderListener listener) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.onDayViewSelected(getAdapterPosition());
    }

    public interface ViewHolderListener {

        void onDayViewSelected(int viewPosition);

    }

}
