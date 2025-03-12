package com.example.easydoc_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.easydoc_app.R;
import java.util.List;

public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.ViewHolder> {
    private final List<String> checklistItems;

    public ChecklistAdapter(List<String> checklistItems) {
        this.checklistItems = checklistItems;
    }

    public List<String> getChecklistItems() {
        return checklistItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checklist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = checklistItems.get(position);
        holder.editChecklistItem.setText(item);

        // Speichern der Änderungen, wenn der Benutzer den Text ändert
        holder.editChecklistItem.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) { // Speichern, wenn der Nutzer das Feld verlässt
                checklistItems.set(position, holder.editChecklistItem.getText().toString());
            }
        });

        holder.deleteButton.setOnClickListener(v -> {
            checklistItems.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return checklistItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        EditText editChecklistItem;
        Button deleteButton;

        ViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            editChecklistItem = itemView.findViewById(R.id.editChecklistItem);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}