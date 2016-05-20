package com.mum.scrum.service;

import com.mum.scrum.model.User;
import com.mum.scrum.viewmodel.Login;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: nadim
 * Date: 4/8/16
 * Time: 11:08 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DashBoard {
     Map<String, Object> populateData(User user);
}
