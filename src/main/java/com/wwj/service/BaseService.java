package com.wwj.service;

public interface BaseService<T> {


    void insert(T t);

    void delete(T t);

    void update(T t);

    void findAll();
}
