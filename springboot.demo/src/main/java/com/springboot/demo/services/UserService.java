package com.springboot.demo.services;

import com.springboot.demo.models.User;
import com.springboot.demo.repositories.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserService {

    @Autowired
    @Qualifier("inMemoryUserRepository")
    IRepository<User> userRepository;

    public Collection<User> getAll(){
        return userRepository.getAll();
    }

    public User get(String id){
        return userRepository.getById(id);
    }

    public void save(User user){
        userRepository.save(user);
    }

}
