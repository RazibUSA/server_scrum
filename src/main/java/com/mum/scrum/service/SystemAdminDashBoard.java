package com.mum.scrum.service;

import com.mum.scrum.dao.UserDao;
import com.mum.scrum.model.User;
import com.mum.scrum.viewmodel.Login;
import com.mum.scrum.viewmodel.PermissionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: nadim
 * Date: 4/8/16
 * Time: 9:38 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("systemAdminDashBoard")
public class SystemAdminDashBoard implements DashBoard {

    @Autowired
    Environment environment;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenGeneratorService tokenGeneratorService;

    @Override
    public Map<String, Object> populateData(User user) {
        Map<String, Object> map = new HashMap<>();
        List<User> userList = userService.getAllUsers();

        map.put("userList", userList);
        map.put("token", tokenGeneratorService.generateToken(user));
        map.put("individual", user);
        map.put("permission", PermissionModel.getSystemAdminPermission());

        return map;
    }


}
