package com.example.easydoc_app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.easydoc_app.adapter.TaskAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.example.easydoc_app.data.model.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.DocumentSnapshot;


import java.util.ArrayList;
import java.util.List;

public class KanbanBoardActivity extends AppCompatActivity {
    public RecyclerView recyclerTodo, recyclerInProgress, recyclerInQA, recyclerDone;
    public TaskAdapter adapterTodo, adapterInProgress, adapterInQA, adapterDone;
    public List<Task> todoList, inProgressList, inQAList, doneList;
    private FirebaseFirestore db;

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

        db = FirebaseFirestore.getInstance();

        setupFirestoreListener();
    }

    private void setupFirestoreListener() {
        db.collection("tasks").addSnapshotListener((querySnapshot, error) -> {
            if (error != null || querySnapshot == null) {
                return;
            }

            // Alle Listen leeren, um sie mit aktuellen Daten zu füllen
            todoList.clear();
            inProgressList.clear();
            inQAList.clear();
            doneList.clear();

            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                Task loadedTask = new Task(
                        document.getString("title"),
                        document.getString("description"),
                        document.getString("status")
                );
                loadedTask.setId(document.getId());

                // Aufgaben den entsprechenden Spalten zuweisen
                switch (loadedTask.getStatus()) {
                    case "ToDo":
                        todoList.add(loadedTask);
                        break;
                    case "In Progress":
                        inProgressList.add(loadedTask);
                        break;
                    case "In QA":
                        inQAList.add(loadedTask);
                        break;
                    case "Done":
                        doneList.add(loadedTask);
                        break;
                }
            }

            // Adapter über Änderungen informieren
            adapterTodo.notifyDataSetChanged();
            adapterInProgress.notifyDataSetChanged();
            adapterInQA.notifyDataSetChanged();
            adapterDone.notifyDataSetChanged();
        });
    }
}
