package com.mum.scrum.service;

import com.mum.scrum.viewmodel.Login;
import com.mum.scrum.model.User;
import com.mum.scrum.viewmodel.ViewModel;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: nadim
 * Date: 4/6/16
 * Time: 7:57 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserService {

    User getUser(long usrId);
    User getUser(String email);
    List<User> getAllUsers();
    void persistUser(User user);
    void deleteUser(User user);
    void validateUser(User user);
    Map<String, Object> handleGetUser(long userId);
    List<String> validateUsrCreation(User user);
    List<String> validateUsrUpdate(User user);
    void updateUser(long userId, User user);
    void saveJpaContact();





}
