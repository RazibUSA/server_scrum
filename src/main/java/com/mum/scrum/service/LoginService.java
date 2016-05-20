package com.mum.scrum.service;

import com.mum.scrum.model.User;
import com.mum.scrum.viewmodel.Login;
import com.mum.scrum.viewmodel.ViewModel;

import java.util.Map;

/**
 * Created by 984609 on 4/8/2016.
 */
public interface LoginService {
    boolean authenticateLogin(Login login);
    Map<String, Object> handleDashBoard(User user);

}
