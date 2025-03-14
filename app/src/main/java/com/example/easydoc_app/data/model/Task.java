package com.example.easydoc_app.data.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class Task implements Serializable {
    private String title;
    private String description;
    private String status;
    private List<String> checklist;
    private Map<String, Boolean> checklistState;
    private String id;

    // Konstruktor
    public Task(String title, String description, String status) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.checklist = new ArrayList<>();
        this.checklistState = new HashMap<>();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter & Setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getChecklist() {
        return checklist;
    }

    public void setChecklist(List<String> checklist) {
        this.checklist = checklist;
    }

    public Map<String, Boolean> getChecklistState() {
        return checklistState;
    }

    public void setChecklistState(Map<String, Boolean> checklistState) {
        this.checklistState = checklistState;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put("title", title);
        taskMap.put("description", description);
        taskMap.put("status", status);
        taskMap.put("checklist", checklist);
        taskMap.put("checklistState", checklistState);
        if (id != null) {
            taskMap.put("id", id);
        }
        return taskMap;
    }
}
