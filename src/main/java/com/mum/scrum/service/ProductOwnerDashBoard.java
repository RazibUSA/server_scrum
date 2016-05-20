package com.mum.scrum.service;

import com.mum.scrum.dao.ProjectDao;
import com.mum.scrum.dao.UserDao;
import com.mum.scrum.model.User;
import com.mum.scrum.viewmodel.Login;
import com.mum.scrum.viewmodel.PermissionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: nadim
 * Date: 4/9/16
 * Time: 12:17 AM
 * To change this template use File | Settings | File Templates.
 */
@Service("productOwnerDashBoard")
public class ProductOwnerDashBoard implements DashBoard {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenGeneratorService tokenGeneratorService;


    @Override
    public Map<String, Object> populateData(User user) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("projectList", projectService.getProjectsByProductOwner(user.getId()));
        dataMap.put("token", tokenGeneratorService.generateToken(user));
        dataMap.put("individual", user);
        dataMap.put("userList", userService.getAllUsers());
        dataMap.put("permission", PermissionModel.getProductOwnerPermission());
        return dataMap;
    }
}
