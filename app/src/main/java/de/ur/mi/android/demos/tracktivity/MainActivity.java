package de.ur.mi.android.demos.tracktivity;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import de.ur.mi.android.demos.tracktivity.tracking.Day;
import de.ur.mi.android.demos.tracktivity.tracking.DayTracker;
import de.ur.mi.android.demos.tracktivity.ui.DaysAdapter;
import de.ur.mi.android.demos.tracktivity.ui.DaysViewItemDecoration;

public class MainActivity extends AppCompatActivity implements DayTracker.DayTrackerListener, DaysAdapter.DaysAdapterListener {

    private DayTracker tracker;
    private DaysAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTracker();
        initUI();
    }

    private void initTracker() {
        tracker = new DayTracker(this);
    }

    private void initUI() {
        setContentView(R.layout.activity_main);
        initDescription();
        initDaysView();
    }

    private void initDescription() {
        TextView tagLineView = findViewById(R.id.app_tag_line);
        TextView descriptionView = findViewById(R.id.app_description);
        setColorForWordsInTextView(tagLineView, Arrays.asList("something"), Arrays.asList(getColor(R.color.ocean_green)));
        setColorForWordsInTextView(descriptionView, Arrays.asList("green", "red"), Arrays.asList(getColor(R.color.ocean_green), getColor(R.color.english_vermillion)));
    }

    private void setColorForWordsInTextView(TextView view, List<String> words, List<Integer> colorIds) {
        String originalText = view.getText().toString();
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(originalText);
        for(int i = 0; i < words.size(); i++) {
            int startSpanAt = originalText.indexOf(words.get(i));
            int endSpanAt = startSpanAt + words.get(i).length();
            sb.setSpan(new ForegroundColorSpan(colorIds.get(i)), startSpanAt, endSpanAt, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        view.setText(sb);
    }


    private void initDaysView() {
        adapter = new DaysAdapter(this);
        adapter.setTrackedDays(tracker.getTrackedDaysInCurrentMonth());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.calendar);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 7));
        recyclerView.addItemDecoration(new DaysViewItemDecoration());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onTrackerUpdated() {
        adapter.setTrackedDays(tracker.getTrackedDaysInCurrentMonth());
    }

    @Override
    public void onDaySelected(Day day) {
        Log.d("Tracktivity", day.state.name());
        tracker.switchStateForDay(day);
    }
}