package com.mum.scrum.dao;

import com.mum.scrum.model.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nadim
 * Date: 4/5/16
 * Time: 11:14 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserDao {

    void saveJpaContact();
    User getUser(long usrId);
    User getUser(String email);
    List<User> getAllUsers();
    void persistUser(User user);
    void deleteUser(User user);

}
