package com.example.easydoc_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easydoc_app.adapter.TaskAdapter;
import com.example.easydoc_app.data.model.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TaskManagerActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private EditText inputTitle, inputDescription;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manager);

        recyclerView = findViewById(R.id.recyclerView);
        inputTitle = findViewById(R.id.inputTitle);
        inputDescription = findViewById(R.id.inputDescription);
        FloatingActionButton addTaskButton = findViewById(R.id.addTaskButton);
        MaterialButton logoutButton = findViewById(R.id.logoutButton);

        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        addTaskButton.setOnClickListener(v -> {
            String title = inputTitle.getText().toString().trim();
            String description = inputDescription.getText().toString().trim();

            if (!title.isEmpty()) {
                taskList.add(new Task(title, description));
                taskAdapter.notifyDataSetChanged();
                inputTitle.setText("");
                inputDescription.setText("");
            } else {
                Toast.makeText(TaskManagerActivity.this, "Task-Titel darf nicht leer sein!", Toast.LENGTH_SHORT).show();
            }
        });

        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(TaskManagerActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
