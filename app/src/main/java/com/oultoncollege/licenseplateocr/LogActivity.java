package com.oultoncollege.licenseplateocr;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.oultoncollege.licenseplateocr.data.AppDatabase;
import com.oultoncollege.licenseplateocr.data.LogEntry;
import com.oultoncollege.licenseplateocr.utils.RecyclerAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LogActivity extends AppCompatActivity {

    private static AppDatabase db;

    private CalendarView calendar;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_layout);

        db = AppDatabase.getInstance(getApplicationContext());

        Date today = new Date();
        setTitle("Logs - " + new SimpleDateFormat("MMM dd, yyyy", Locale.CANADA).format(today));
        long dbDate = Long.parseLong(new SimpleDateFormat("yyyyMMdd", Locale.CANADA).format(today));
        recyclerAdapter = new RecyclerAdapter(db.logEntryDao().getLogsByDate(dbDate));
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        calendar = findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                calendar.setVisibility(View.GONE);
                String date = new SimpleDateFormat("MMM dd, yyyy", Locale.CANADA).format(new Date((year - 1900), month, dayOfMonth));
                setTitle("Logs - " + date);

                recyclerAdapter.setLogEntries(db.logEntryDao().getLogsByDate(getFormattedDate(year, month, dayOfMonth)));
                recyclerAdapter.notifyDataSetChanged();
            }
        });

        recyclerView.setAdapter(recyclerAdapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                recyclerAdapter.removeAt(position);

                TextView logCard = viewHolder.itemView.findViewById(R.id.log_id);
                int logID = Integer.parseInt(logCard.getText().toString());
                LogEntry logToDelete = db.logEntryDao().getLogById(logID);
                db.logEntryDao().remove(logToDelete);

                makeUndoSnackbar(logToDelete, position);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.date_picker) {
            calendar.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
            calendar.setVisibility(View.VISIBLE);
        }

        return true;
    }

    public long getFormattedDate(int year, int month, int dayOfMonth) {
        String strMonth, strDayOfMonth;

        if (dayOfMonth < 10)
            strDayOfMonth = "0" + dayOfMonth;
        else
            strDayOfMonth = String.valueOf(dayOfMonth);
        if (month < 10)
            strMonth = "0" + (month + 1);
        else
            strMonth = String.valueOf(month);

        return Long.parseLong(year + strMonth + strDayOfMonth);
    }

    public void makeUndoSnackbar(LogEntry logToDelete, int exitPosition) {
        final LogEntry logToRestore = logToDelete;
        final int entryPosition = exitPosition;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.logEntryDao().add(logToRestore);
                recyclerAdapter.addAt(logToRestore, entryPosition);
            }
        };

        Snackbar.make(recyclerView, getString(R.string.log_delete), Snackbar.LENGTH_LONG)
                .setAction(R.string.undo_delete, listener)
                .show();
    }
}
