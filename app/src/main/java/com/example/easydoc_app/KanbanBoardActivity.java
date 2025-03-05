package com.example.easydoc_app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easydoc_app.TaskAdapter;

import java.util.ArrayList;
import java.util.List;

public class KanbanBoardActivity extends AppCompatActivity {
    private RecyclerView recyclerTodo, recyclerInProgress, recyclerInQA, recyclerDone;
    private TaskAdapter adapterTodo, adapterInProgress, adapterInQA, adapterDone;
    private List<String> todoList, inProgressList, inQAList, doneList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResources().getIdentifier("activity_kanban_board", "layout", getPackageName()));

        recyclerTodo = findViewById(getResources().getIdentifier("recycler_todo", "id", getPackageName()));
        recyclerInProgress = findViewById(getResources().getIdentifier("recycler_in_progress", "id", getPackageName()));
        recyclerInQA = findViewById(getResources().getIdentifier("recycler_in_qa", "id", getPackageName()));
        recyclerDone = findViewById(getResources().getIdentifier("recycler_done", "id", getPackageName()));

        todoList = new ArrayList<>();
        inProgressList = new ArrayList<>();
        inQAList = new ArrayList<>();
        doneList = new ArrayList<>();

        loadData();

        adapterTodo = new TaskAdapter(todoList);
        adapterInProgress = new TaskAdapter(inProgressList);
        adapterInQA = new TaskAdapter(inQAList);
        adapterDone = new TaskAdapter(doneList);

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
        todoList.add("Task 1");
        todoList.add("Task 2");
        inProgressList.add("Task 3");
        inQAList.add("Task 4");
        doneList.add("Task 5");
    }
}
