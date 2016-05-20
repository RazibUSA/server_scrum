package com.mum.scrum.restController;

import com.mum.scrum.model.User;
import com.mum.scrum.service.FormValidatorService;
import com.mum.scrum.service.LoginService;
import com.mum.scrum.service.UserService;
import com.mum.scrum.utility.Utility;
import com.mum.scrum.viewmodel.Login;
import com.mum.scrum.viewmodel.ViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: nadim
 * Date: 4/6/16
 * Time: 4:54 PM
 * To change this template use File | Settings | File Templates.
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @Autowired
    private FormValidatorService formValidatorService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<Void> getLoginUser() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE} , method = RequestMethod.POST)
    public ResponseEntity<ViewModel> loginUser(@Valid @RequestBody Login login, BindingResult bindResult) {
        if (bindResult.hasErrors()) {
            List<String> errorList = formValidatorService.doFormValidation(bindResult);
            return new ResponseEntity<>(Utility.populateViewModel(Utility.ERROR_STATUS_CODE, errorList), HttpStatus.BAD_REQUEST);
        }
        if (!loginService.authenticateLogin(login)) {
            return new ResponseEntity<>(Utility.populateViewModel(
                    Utility.NOT_LOGGED_IN_STATUS_CODE, Arrays.asList("forbidden")),
                    HttpStatus.FORBIDDEN);
        }
        ///TODO  load dashboard
        User user = userService.getUser(login.getEmail());
        Map<String, Object> map  = loginService.handleDashBoard(user);

        return new ResponseEntity<>(Utility.populateViewModel(
                Utility.SUCCESS_STATUS_CODE, Arrays.asList("Successfully logged in"), map),
                HttpStatus.OK);
    }
}
