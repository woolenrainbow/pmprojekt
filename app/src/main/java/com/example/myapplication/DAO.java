package com.example.myapplication;

import java.util.List;

public interface DAO<T> {
    void save(T element);
    List<T> getAll();
}
