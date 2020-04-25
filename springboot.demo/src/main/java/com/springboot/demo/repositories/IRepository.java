package com.springboot.demo.repositories;

import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface IRepository<T> {

    Collection<T> getAll();

    T getById(String id);

    T save(T t);

    void delete(String id);

}
