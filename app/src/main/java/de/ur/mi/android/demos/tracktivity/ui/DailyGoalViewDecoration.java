package de.ur.mi.android.demos.tracktivity.ui;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DailyGoalViewDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int offset = (int) ((parent.getWidth() / 7) * 0.15);
        outRect.set(offset, offset, offset, offset);
    }
}
