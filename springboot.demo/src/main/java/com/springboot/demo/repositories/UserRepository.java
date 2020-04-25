package com.springboot.demo.repositories;
import com.springboot.demo.models.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

@Repository("inMemoryUserRepository")
public class UserRepository implements IRepository<User> {

    private HashMap<Integer, User> users = new HashMap();

    public UserRepository() {
        users.put(1, new User(1, "mark", 21, new Date()));
        users.put(2, new User(2, "bob", 31, new Date()));
        users.put(3, new User(3, "alice",41, new Date()));
    }

    @Override
    public Collection<User> getAll() {
        return users.values();
    }

    @Override
    public User getById(String id) {
        return users.get(Integer.parseInt(id));
    }

    @Override
    public User save(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void delete(String id){
        users.remove(Integer.parseInt(id));
    }
}
