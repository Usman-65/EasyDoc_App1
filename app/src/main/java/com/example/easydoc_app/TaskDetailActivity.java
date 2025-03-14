package com.example.easydoc_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView;
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
    private RecyclerView checklistRecyclerView;
    private ChecklistAdapter checklistAdapter;
    private List<String> checklistItems;
    private Button saveButton, backButton, addChecklistItemButton;
    private String taskId;
    private FirebaseFirestore db;
    private Task task;
    private Spinner spinnerTaskStatus;
    private String selectedStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        db = FirebaseFirestore.getInstance();
        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        checklistRecyclerView = findViewById(R.id.checklistRecyclerView);
        saveButton = findViewById(R.id.saveButton);
        backButton = findViewById(R.id.backButton);
        addChecklistItemButton = findViewById(R.id.addChecklistItemButton);
        spinnerTaskStatus = findViewById(R.id.spinnerTaskStatus);

        checklistItems = new ArrayList<>();
        checklistAdapter = new ChecklistAdapter(checklistItems);
        checklistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        checklistRecyclerView.setAdapter(checklistAdapter);

        // Status-Optionen für das Kanban-Board
        String[] statuses = {"ToDo", "In Progress", "In QA", "Done"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statuses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTaskStatus.setAdapter(adapter);


        // Task-Daten abrufen
        Task task = (Task) getIntent().getSerializableExtra("task");
        if (task != null) {
            editTitle.setText(task.getTitle());
            editDescription.setText(task.getDescription());

            // Setze den aktuellen Status im Spinner
            int statusPosition = adapter.getPosition(task.getStatus());
            spinnerTaskStatus.setSelection(statusPosition);

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

        // Status speichern, wenn Spinner geändert wird
        spinnerTaskStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStatus = statuses[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedStatus = task != null ? task.getStatus() : "ToDo";
            }
        });

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

                    if (document.getString("status") != null) {
                        int statusPosition = adapter.getPosition(document.getString("status"));
                        spinnerTaskStatus.setSelection(statusPosition);
                    }
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
            checklistItems.add("");
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
        updatedTask.put("status", selectedStatus);
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
