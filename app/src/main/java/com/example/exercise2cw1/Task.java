package com.example.exercise2cw1;

public class Task {
    private String name;
    private String deadline;
    private String description;

    public Task(String name, String deadline, String description) {
        this.name = name;
        this.deadline = deadline;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
