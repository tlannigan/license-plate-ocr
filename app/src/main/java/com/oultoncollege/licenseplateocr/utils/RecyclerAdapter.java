package com.oultoncollege.licenseplateocr.utils;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.oultoncollege.licenseplateocr.R;
import com.oultoncollege.licenseplateocr.data.LogEntry;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.LogViewHolder> {

    private List<LogEntry> logEntries;

    public static class LogViewHolder extends RecyclerView.ViewHolder {
        public CardView logCardView;
        public TextView logTextView;
        public TextView logID;

        public LogViewHolder(CardView card) {
            super(card);
            logCardView = card;
            logTextView = card.findViewById(R.id.log_details);
            logID = card.findViewById(R.id.log_id);
        }
    }

    public RecyclerAdapter(List<LogEntry> logEntries) {
        this.logEntries = logEntries;
    }

    @Override
    public RecyclerAdapter.LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView card = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.log_card_layout, parent, false);

        return new LogViewHolder(card);
    }

    public List<LogEntry> getLogEntries() {
        return logEntries;
    }

    public void setLogEntries(List<LogEntry> logEntries) {
        this.logEntries = logEntries;
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        holder.logTextView.setText(logEntries.get(position).toString());
        holder.logID.setText(String.valueOf(logEntries.get(position).getId()));
    }

    public void removeAt(int position) {
        logEntries.remove(position);
        notifyItemRemoved(position);
    }

    public void addAt(LogEntry logEntry, int position) {
        logEntries.add(position, logEntry);
        notifyItemInserted(position);
    }


    @Override
    public int getItemCount() {
        return logEntries.size();
    }
}
