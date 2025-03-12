package com.example.easydoc_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.text.Editable;
import android.text.TextWatcher;
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

        holder.editChecklistItem.removeTextChangedListener(holder.textWatcher);

        holder.textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Während getippt wird, gleich den neuen Wert speichern
                checklistItems.set(holder.getAdapterPosition(), s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        };

        // Listener setzen
        holder.editChecklistItem.addTextChangedListener(holder.textWatcher);

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
        TextWatcher textWatcher;

        ViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            editChecklistItem = itemView.findViewById(R.id.editChecklistItem);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}