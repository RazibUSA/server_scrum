package com.mum.scrum.service;

import com.mum.scrum.model.User;
import com.mum.scrum.viewmodel.Login;
import com.mum.scrum.viewmodel.PermissionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: cheng
 * Date: 4/18/16
 * Time: 1:49 AM
 * To c*/

@Service("developerDashBoard")
public class DeveloperDashBoard implements DashBoard {


    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenGeneratorService tokenGeneratorService;


    @Override
    public Map<String, Object> populateData(User user) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("projectList", projectService.getProjectsByDeveloper(user.getId()));
        dataMap.put("token", tokenGeneratorService.generateToken(user));
        dataMap.put("individual", user);
        dataMap.put("permission", PermissionModel.getDeveloperPermission());
        return dataMap;
    }
}
