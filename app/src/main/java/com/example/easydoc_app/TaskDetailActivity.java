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
import com.example.easydoc_app.adapter.ChecklistAdapter;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import android.widget.Toast;
import com.google.firebase.firestore.DocumentReference;



public class TaskDetailActivity extends AppCompatActivity {
    private EditText editTitle, editDescription;
    private TextView taskStatus;
    private RecyclerView checklistRecyclerView;
    private ChecklistAdapter checklistAdapter;
    private List<String> checklistItems;
    private Button saveButton, backButton, addChecklistItemButton;
    private String taskId;
    private FirebaseFirestore db;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        db = FirebaseFirestore.getInstance();
        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        taskStatus = findViewById(R.id.taskStatus);
        checklistRecyclerView = findViewById(R.id.checklistRecyclerView);
        saveButton = findViewById(R.id.saveButton);
        backButton = findViewById(R.id.backButton);
        addChecklistItemButton = findViewById(R.id.addChecklistItemButton);

        checklistItems = new ArrayList<>();
        checklistAdapter = new ChecklistAdapter(checklistItems);
        checklistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        checklistRecyclerView.setAdapter(checklistAdapter);

        // Task-Daten abrufen
        Task task = (Task) getIntent().getSerializableExtra("task");
        if (task != null) {
            editTitle.setText(task.getTitle());
            editDescription.setText(task.getDescription());
            taskStatus.setText("Status: " + task.getStatus());
            taskId = task.getId();

            if (task.getChecklist() != null) {
                checklistItems.addAll(task.getChecklist());
                checklistAdapter.notifyDataSetChanged();
            }
        }
        // Falls taskId null ist, versuchen, sie aus dem Intent zu holen
        if (taskId == null || taskId.isEmpty()) {
            taskId = getIntent().getStringExtra("task_id");
        }

        // Falls taskId noch immer null ist, eine Fehlermeldung anzeigen
        if (taskId == null || taskId.isEmpty()) {
            Toast.makeText(this, "Fehler: Task-ID fehlt!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Falls noch keine Checklist-Daten vorhanden sind, aus Firestore laden
        db.collection("tasks").document(taskId).get().addOnSuccessListener(document -> {
            if (document.exists()) {
                if (task == null) { // Falls task nicht aus Intent geladen wurde, hier setzen
                    editTitle.setText(document.getString("title"));
                    editDescription.setText(document.getString("description"));
                    taskStatus.setText("Status: " + document.getString("status"));
                }
                List<String> checklist = (List<String>) document.get("checklist");
                if (checklist != null) {
                    checklistItems.addAll(checklist);
                    checklistAdapter.notifyDataSetChanged();
                }
            }
        });

        // Checklisten Punkt hinzufügen
        addChecklistItemButton.setOnClickListener(v -> {
            checklistItems.add("Neuer Punkt");
            checklistAdapter.notifyDataSetChanged();
        });

        // Task speichern
        saveButton.setOnClickListener(v -> saveTaskToFirestore());
        backButton.setOnClickListener(v -> finish());
    }

    private void saveTaskToFirestore() {
        String updatedTitle = editTitle.getText().toString().trim();
        String updatedDescription = editDescription.getText().toString().trim();
        List<String> updatedChecklist = checklistAdapter.getChecklistItems();

        if (taskId == null || taskId.isEmpty()) {
            Toast.makeText(this, "Fehler: Task-ID fehlt!", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> updatedTask = new HashMap<>();
        updatedTask.put("title", updatedTitle);
        updatedTask.put("description", updatedDescription);
        updatedTask.put("status", taskStatus.getText().toString());
        updatedTask.put("checklist", updatedChecklist);

        DocumentReference taskRef = db.collection("tasks").document(taskId);
        taskRef.update(updatedTask)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(TaskDetailActivity.this, "Task aktualisiert!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(TaskDetailActivity.this, "Fehler beim Aktualisieren!", Toast.LENGTH_SHORT).show());
    }
}
