package com.mum.scrum.service;

import com.mum.scrum.dao.UserDao;
import com.mum.scrum.model.Role;
import com.mum.scrum.utility.Utility;
import com.mum.scrum.viewmodel.Login;
import com.mum.scrum.model.User;
import com.mum.scrum.viewmodel.PermissionModel;
import com.mum.scrum.viewmodel.ViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: nadim
 * Date: 4/6/16
 * Time: 7:59 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    Environment environment;

    @Autowired
    private UserDao userDao;

    @Override
    public User getUser(long usrId) {
        return userDao.getUser(usrId);
    }

    @Override
    public User getUser(String email) {
        return userDao.getUser(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public void persistUser(User user) {
        userDao.persistUser(user);
    }

    @Override
    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }

    @Override
    public void validateUser(User user) {
        ///TODO
    }

    @Override
    public Map<String, Object> handleGetUser(long userId) {
        ///validate user
        Map<String, Object> map = new HashMap<>();
        User user = getUser(userId);
        map.put("userList", Arrays.asList(user));
        map.put("permission", PermissionModel.getProductOwnerPermission());//TODO add role to return permission
        return map;
    }


    public List<String> validateUsrCreation(User user) {

        if (StringUtils.isEmpty(user.getPassword())) {
            return Arrays.asList("Password is empty.");
        }
        ///check permission
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getParameter("token");

        if (!StringUtils.isEmpty(token)) {
            String[] split = token.split("\\|");
            if (!Utility.hasPermission("canCreateUser", Integer.valueOf(split[1]))) {
                return Arrays.asList("Unauthorized access!!!");
            }
        }

        //is user already exist
        User existedUser = userDao.getUser(user.getEmail());
        if (existedUser != null) {
            return Arrays.asList("User already exists!!!");
        }
        return Arrays.asList();   //TODO return better way to validation error
    }

    @Override
    public List<String> validateUsrUpdate(User user) {
         Map<String, Object> map = new HashMap<>();
        ///check permission
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getParameter("token");

        if (!StringUtils.isEmpty(token)) {
            String[] split = token.split("\\|");
            if (!Utility.hasPermission("canUpdateUser", Integer.valueOf(split[1]))) {
                return Arrays.asList("Unauthorized access!!!");
            }
        }

        ////password field is handled seperately
        if (!StringUtils.isEmpty(user.getPassword())) {
            return Arrays.asList("Should not contain password!!!");
        }
        return Arrays.asList();
    }

    @Override
    public void updateUser(long userId, User user) {
        User userObj = userDao.getUser(userId);
        Role role = new Role();
        role.setId(user.getRole().getId());
        userObj.setEmail(user.getEmail());
        userObj.setFirstName(user.getFirstName());
        userObj.setLastName(user.getLastName());
        userObj.setRole(role);

        userDao.persistUser(userObj);
    }

    @Override
    public void saveJpaContact() {
        userDao.saveJpaContact();
    }
}
