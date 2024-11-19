package com.example.exercise2cw1;
import com.example.exercise2cw1.Task;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Task> taskList;
    private TaskAdapter adapter;
    private EditText editTextTaskName, editTextTaskDeadline, editTextTaskDescription;
    private Button btnAddTask;
    private ListView listViewTasks;
    private int editTaskIndex = -1; // Track the task being edited

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTaskName = findViewById(R.id.editTextTaskName);
        editTextTaskDeadline = findViewById(R.id.editTextTaskDeadline);
        editTextTaskDescription = findViewById(R.id.editTextTaskDescription);
        btnAddTask = findViewById(R.id.btnAddTask);
        listViewTasks = findViewById(R.id.listViewTasks);

        // Initialize task list and adapter
        taskList = new ArrayList<>();
        adapter = new TaskAdapter(taskList);
        listViewTasks.setAdapter(adapter);

        // Add or edit task when "Add Task" button is clicked
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrEditTask();
            }
        });
    }

    // Add or edit a task
    private void addOrEditTask() {
        String name = editTextTaskName.getText().toString();
        String deadline = editTextTaskDeadline.getText().toString();
        String description = editTextTaskDescription.getText().toString();

        if (!name.isEmpty() && !deadline.isEmpty() && !description.isEmpty()) {
            if (editTaskIndex == -1) {
                // Add new task
                taskList.add(new Task(name, deadline, description));
            } else {
                // Edit existing task
                Task task = taskList.get(editTaskIndex);
                task.setName(name);
                task.setDeadline(deadline);
                task.setDescription(description);
                editTaskIndex = -1; // Reset index after edit
                btnAddTask.setText("Add Task"); // Reset button text to "Add Task"
            }
            adapter.notifyDataSetChanged();
            clearInputFields();
        } else {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }
    }

    // Clear the input fields after adding/editing a task
    private void clearInputFields() {
        editTextTaskName.setText("");
        editTextTaskDeadline.setText("");
        editTextTaskDescription.setText("");
    }

    // Custom ArrayAdapter to handle the task items
    private class TaskAdapter extends ArrayAdapter<Task> {
        private ArrayList<Task> tasks;

        public TaskAdapter(ArrayList<Task> tasks) {
            super(MainActivity.this, R.layout.task_item, tasks);
            this.tasks = tasks;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.task_item, parent, false);
            }

            // Get the current task
            Task task = tasks.get(position);

            // Set task name, deadline, and description in the TextViews
            TextView taskName = convertView.findViewById(R.id.taskName);
            TextView taskDeadline = convertView.findViewById(R.id.taskDeadline);
            TextView taskDescription = convertView.findViewById(R.id.taskDescription);

            taskName.setText(task.getName());
            taskDeadline.setText(task.getDeadline());
            taskDescription.setText(task.getDescription());

            // Edit button functionality
            Button btnEditTask = convertView.findViewById(R.id.btnEditTask);
            btnEditTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editTask(position);
                }
            });

            // Delete button functionality
            Button btnDeleteTask = convertView.findViewById(R.id.btnDeleteTask);
            btnDeleteTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteTask(position);
                }
            });

            return convertView;
        }
    }

    // Edit a selected task
    private void editTask(int position) {
        Task task = taskList.get(position);
        editTextTaskName.setText(task.getName());
        editTextTaskDeadline.setText(task.getDeadline());
        editTextTaskDescription.setText(task.getDescription());

        editTaskIndex = position;
        btnAddTask.setText("Update Task");
    }

    // Delete a selected task
    private void deleteTask(int position) {
        taskList.remove(position);
        adapter.notifyDataSetChanged();
        Toast.makeText(MainActivity.this, "Task deleted", Toast.LENGTH_SHORT).show();
    }
}


