package com.example.taskapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.taskapp.Adapter.ToDoAdapter;
import com.example.taskapp.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView tasksRV;
    private ToDoAdapter tasksAdapter;

    private List<ToDoModel> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        taskList = new ArrayList<>();

        tasksRV = findViewById(R.id.tasksRecycleView);
        tasksRV.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter(this);
        tasksRV.setAdapter(tasksAdapter);

        ToDoModel task = new ToDoModel();
        task.setTask("This is a test task!");
        task.setStatus(0);
        task.setId(1);

        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);

        tasksAdapter.setTasks(taskList);
    }
}