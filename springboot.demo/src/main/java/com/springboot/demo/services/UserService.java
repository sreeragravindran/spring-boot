package com.springboot.demo.services;

import com.springboot.demo.models.User;
import com.springboot.demo.repositories.IRepository;
import com.springboot.demo.services.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {

    @Autowired
    @Qualifier("inMemoryUserRepository")
    IRepository<User> userRepository;

    public Collection<User> getAll(){
        return userRepository.getAll();
    }

    public User get(String id){
        User user = userRepository.getById(id);
        if(user == null){
            throw new UserNotFoundException("id-"+id);
        }
        return user;
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public void delete(String id){
        User user = get(id);
        userRepository.delete(id);
    }

}
