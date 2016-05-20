package com.mum.scrum.restController;

import com.mum.scrum.config.ScrumConfig;
import com.mum.scrum.dao.UserDao;
import com.mum.scrum.model.Message;
import com.mum.scrum.model.Role;
import com.mum.scrum.model.User;
import com.mum.scrum.service.FormValidatorService;
import com.mum.scrum.service.UserService;
import com.mum.scrum.utility.Utility;
import com.mum.scrum.viewmodel.ViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: nadim
 * Date: 4/4/16
 * Time: 8:47 PM
 * To change this template use File | Settings | File Templates.
 */
@RestController
public class UserController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;
    
    @Autowired
    private FormValidatorService formValidatorService;

    @Autowired
    private Environment env;

    @RequestMapping("/hello/{player}")
    public Message message(@PathVariable String player) {

        log.warn("logging....");

        userService.saveJpaContact();


        // userDao.saveJpaContact();
        Message msg = new Message(player, "Hello " + player);
        return msg;
    }

    @RequestMapping(value = "/user/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    public ResponseEntity<ViewModel> getUser(@PathVariable("userId") long userId) {

        //Assume that anyone can view user info
        Map<String, Object> dataMap = userService.handleGetUser(userId);
        return new ResponseEntity<>(
                Utility.populateViewModel(Utility.SUCCESS_STATUS_CODE, Arrays.asList("Successful"), dataMap),
                HttpStatus.OK);

    }

    @RequestMapping(value = "/user", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
    public ResponseEntity<ViewModel> createUser(@Valid @RequestBody User user, BindingResult bindResult) {
        //form validation
        if (bindResult.hasErrors()) {
            List<String> errorList = formValidatorService.doFormValidation(bindResult);
            return new ResponseEntity<>(Utility.populateViewModel(Utility.ERROR_STATUS_CODE, errorList), HttpStatus.BAD_REQUEST);
        }

        //business case validation
        List<String> validations = userService.validateUsrCreation(user);
        if (hasAnyLogicalError(validations)) {     //error happens
            return new ResponseEntity<>(Utility.populateViewModel(Utility.ERROR_STATUS_CODE, validations), HttpStatus.BAD_REQUEST);
        }

        userService.persistUser(user);
        Map<String, Object> map = new HashMap<>();
        map.put("userList", Arrays.asList(userService.getUser(user.getId())));
        //TODO to uri
        return new ResponseEntity<>(
                Utility.populateViewModel(Utility.SUCCESS_STATUS_CODE, Arrays.asList("User has been created successfully!"), map),
                HttpStatus.CREATED);

    }

    private boolean hasAnyLogicalError(List<String> validationList) {
        return (validationList != null && validationList.size() > 0);
    }

    @ResponseBody
    @RequestMapping(value = "/user/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.PUT)
    public ResponseEntity<ViewModel> updateUser(@PathVariable("userId") long userId, @Valid @RequestBody User user, BindingResult bindResult) {
        //form validation
        if (bindResult.hasErrors()) {
            List<String> errorList = formValidatorService.doFormValidation(bindResult);
            return new ResponseEntity<>(Utility.populateViewModel(Utility.ERROR_STATUS_CODE, errorList), HttpStatus.BAD_REQUEST);
        }

        List<String> validations = userService.validateUsrUpdate(user);
        if (hasAnyLogicalError(validations)) {
            return new ResponseEntity<>(Utility.populateViewModel(Utility.ERROR_STATUS_CODE, validations), HttpStatus.BAD_REQUEST);

        }

        userService.updateUser(userId, user);
        user = userService.getUser(userId);

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("userList", Arrays.asList(user));
        return new ResponseEntity<>(Utility.populateViewModel(
                Utility.SUCCESS_STATUS_CODE, Arrays.asList("User has been updated successfully!"), dataMap),
                HttpStatus.OK);
    }

}
