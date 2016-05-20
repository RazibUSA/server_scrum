package com.mum.scrum.service;

import com.mum.scrum.dao.ProjectDao;
import com.mum.scrum.model.User;
import com.mum.scrum.viewmodel.Login;
import com.mum.scrum.viewmodel.PermissionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: cheng
 * Date: 4/18/16
 * Time: 1:49 AM
 * To change this template use File | Settings | File Templates.
 */
@Service("scrumMasterDashBoard")
public class ScrumMasterDashBoard implements DashBoard {

    @Autowired
    private TokenGeneratorService tokenGeneratorService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Override
    public Map<String, Object> populateData(User user) {
        Map<String, Object> map = new HashMap<>();

        map.put("projectList", projectService.getProjectsByScrumMaster(user.getId()));
        map.put("token", tokenGeneratorService.generateToken(user));
        map.put("individual", user);
        map.put("userList", userService.getAllUsers());
        map.put("permission", PermissionModel.getScrumMasterPermission());

        return map;
    }
}
