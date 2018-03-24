package com.Alina.spring.market.interfacesDAO;

import com.Alina.spring.market.entities.Role;
import com.Alina.spring.market.entities.User;

import java.util.List;

public interface UserDAO {

    public List<User> getAllUsers();
    public List<Role> getAllRoles();
    public void addUser(User u);
    public User getUserByLoginAndPassword(String login, String password);
    public User getUserById(long id);
    public void deleteUser(User u);
}

