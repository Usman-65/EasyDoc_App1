package com.example.easydoc_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import android.view.MenuItem;

import com.example.easydoc_app.data.model.Task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.annotation.NonNull;

import com.example.easydoc_app.adapter.TaskAdapter;
import com.example.easydoc_app.adapter.ChecklistAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;


import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class TaskManagerActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private List<Task> taskList;

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private EditText inputTitle, inputDescription;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manager);

        recyclerView = findViewById(R.id.recyclerView);
        inputTitle = findViewById(R.id.inputTitle);
        inputDescription = findViewById(R.id.inputDescription);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewCompat.setOnApplyWindowInsetsListener(toolbar, (v, insets) -> {
            int statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top;
            v.setPadding(0, statusBarHeight, 0, 0);
            return insets;
        });

    // Zurück-Pfeil
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("EasyDoc App");
        }

        MaterialButton addTaskButton = findViewById(R.id.addTaskButton);
        MaterialButton kanbanBoardButton = findViewById(R.id.kanbanBoardButton);
        MaterialButton logoutButton = findViewById(R.id.logoutButton);

        db = FirebaseFirestore.getInstance();
        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        addTaskButton.setOnClickListener(v -> {
            String title = inputTitle.getText().toString().trim();
            String description = inputDescription.getText().toString().trim();

            if (!title.isEmpty()) {
                Task task = new Task(title, description, "ToDo");

                // Daten in Firestore speichern
                db.collection("tasks")
                        .add(task.toMap())  // Task in Firestore speichern
                        .addOnSuccessListener(documentReference -> {
                            taskList.add(task);
                            taskAdapter.notifyDataSetChanged();
                            Toast.makeText(TaskManagerActivity.this, "Task gespeichert!", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> Toast.makeText(TaskManagerActivity.this, "Fehler beim Speichern!", Toast.LENGTH_SHORT).show());

                inputTitle.setText("");
                inputDescription.setText("");
            } else {
                Toast.makeText(TaskManagerActivity.this, "Task-Titel darf nicht leer sein!", Toast.LENGTH_SHORT).show();
            }
        });

        // Neuer Button: Zum Kanban Board
        kanbanBoardButton.setOnClickListener(v -> {
            Intent intent = new Intent(TaskManagerActivity.this, KanbanBoardActivity.class);
            startActivity(intent);
        });

        // Click Listener für den Logout-Button
        logoutButton.setOnClickListener(v -> {
            // Falls Firebase-Logout notwendig ist:
            // FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(TaskManagerActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStart() {
        super.onStart();
        loadTasksFromFirestore();
    }

    private void loadTasksFromFirestore() {
        db.collection("tasks").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                taskList.clear();
                for (DocumentSnapshot document : task.getResult()) {
                    Task loadedTask = new Task(
                            document.getString("title"),
                            document.getString("description"),
                            document.getString("status")
                    );
                    loadedTask.setId(document.getId());
                    List<String> checklist = (List<String>) document.get("checklist");
                    if (checklist != null) {
                        loadedTask.setChecklist(checklist);
                    }
                    taskList.add(loadedTask);
                }
                taskAdapter.notifyDataSetChanged();
            }
        });
    }

}