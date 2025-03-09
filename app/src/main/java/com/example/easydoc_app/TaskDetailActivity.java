package com.example.easydoc_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.easydoc_app.data.model.Task;


import java.util.ArrayList;
import java.util.List;

public class TaskDetailActivity extends AppCompatActivity {
    private EditText editTitle, editDescription;
    private TextView taskStatus;
    private RecyclerView checklistRecyclerView;
    private ChecklistAdapter checklistAdapter;
    private List<String> checklistItems;
    private Button saveButton, backButton, addChecklistItemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        taskStatus = findViewById(R.id.taskStatus);
        checklistRecyclerView = findViewById(R.id.checklistRecyclerView);
        saveButton = findViewById(R.id.saveButton);
        backButton = findViewById(R.id.backButton);
        addChecklistItemButton = findViewById(R.id.addChecklistItemButton);

        // Beispiel-Daten abrufen (später durch echte Daten ersetzen)
        Task task = (Task) getIntent().getSerializableExtra("task");
        if (task != null) {
            editTitle.setText(task.getTitle());
            editDescription.setText(task.getDescription());
            taskStatus.setText("Status: " + task.getStatus());
        }


        checklistItems = new ArrayList<>();
        checklistAdapter = new ChecklistAdapter(checklistItems);
        checklistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        checklistRecyclerView.setAdapter(checklistAdapter);

        addChecklistItemButton.setOnClickListener(v -> {
            checklistItems.add("Neuer Punkt");
            checklistAdapter.notifyDataSetChanged();
        });

        saveButton.setOnClickListener(v -> finish());
        backButton.setOnClickListener(v -> finish());
    }

    // **Inner Class für ChecklistAdapter**
    private static class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.ViewHolder> {
        private final List<String> checklistItems;

        public ChecklistAdapter(List<String> checklistItems) {
            this.checklistItems = checklistItems;
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
            holder.checkBox.setText(item);
            holder.deleteButton.setOnClickListener(v -> {
                checklistItems.remove(position);
                notifyDataSetChanged();
            });
        }

        @Override
        public int getItemCount() {
            return checklistItems.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            CheckBox checkBox;
            Button deleteButton;

            ViewHolder(View itemView) {
                super(itemView);
                checkBox = itemView.findViewById(R.id.checkBox);
                deleteButton = itemView.findViewById(R.id.deleteButton);
            }
        }
    }
}
