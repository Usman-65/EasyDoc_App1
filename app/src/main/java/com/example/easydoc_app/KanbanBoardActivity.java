package com.example.easydoc_app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.easydoc_app.adapter.TaskAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.example.easydoc_app.data.model.Task;


import java.util.ArrayList;
import java.util.List;

public class KanbanBoardActivity extends AppCompatActivity {
    public RecyclerView recyclerTodo, recyclerInProgress, recyclerInQA, recyclerDone;
    public TaskAdapter adapterTodo, adapterInProgress, adapterInQA, adapterDone;
    public List<Task> todoList, inProgressList, inQAList, doneList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanban_board);
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> finish());


        recyclerTodo = findViewById(R.id.recyclerToDo);
        recyclerInProgress = findViewById(R.id.recyclerInProgress);
        recyclerInQA = findViewById(R.id.recyclerInQA);
        recyclerDone = findViewById(R.id.recyclerDone);

        todoList = new ArrayList<>();
        inProgressList = new ArrayList<>();
        inQAList = new ArrayList<>();
        doneList = new ArrayList<>();

        loadData();

        adapterTodo = new TaskAdapter(todoList, this);
        adapterInProgress = new TaskAdapter(inProgressList, this);
        adapterInQA = new TaskAdapter(inQAList, this);
        adapterDone = new TaskAdapter(doneList, this);

        recyclerTodo.setLayoutManager(new LinearLayoutManager(this));
        recyclerTodo.setAdapter(adapterTodo);

        recyclerInProgress.setLayoutManager(new LinearLayoutManager(this));
        recyclerInProgress.setAdapter(adapterInProgress);

        recyclerInQA.setLayoutManager(new LinearLayoutManager(this));
        recyclerInQA.setAdapter(adapterInQA);

        recyclerDone.setLayoutManager(new LinearLayoutManager(this));
        recyclerDone.setAdapter(adapterDone);
    }

    private void loadData() {
        todoList.add(new Task("Task 1", "Beschreibung für Task 1", "ToDo"));
        todoList.add(new Task("Task 2", "Beschreibung für Task 2", "ToDo"));
        inProgressList.add(new Task("Task 3", "Beschreibung für Task 3", "In Progress"));
        inQAList.add(new Task("Task 4", "Beschreibung für Task 4", "In QA"));
        doneList.add(new Task("Task 5", "Beschreibung für Task 5", "Done"));
    }
}
