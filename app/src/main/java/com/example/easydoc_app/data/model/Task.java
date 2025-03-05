package com.example.easydoc_app.data.model;

public class Task {
    private String title;
    private String description;

    // Konstruktor
    public Task(String title, String description) {
        this.title = title;
        this.description = description;
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
}
