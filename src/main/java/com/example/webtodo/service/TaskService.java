package com.example.webtodo.service;

import com.example.webtodo.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    List<Task> findAll();

    Optional<Task> getTask(int id);

    void insert(Task task);

    void update(Task task);

    void deleteById(int id);

    List<Task> findByType(int typId);

}
