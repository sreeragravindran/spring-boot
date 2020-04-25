package com.springboot.demo.repositories;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface IRepository<T> {

    Collection<T> getAll();

    T getById(String id);

    void save(T t);

}
