package com.mum.scrum.restController;

import com.mum.scrum.model.User;
import com.mum.scrum.service.LoginService;
import com.mum.scrum.service.UserService;
import com.mum.scrum.utility.Utility;
import com.mum.scrum.viewmodel.ViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by 984609 on 4/20/2016.
 */
@RestController
public class DashBoardController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/loaddashboard", method = RequestMethod.GET)
    public ResponseEntity<ViewModel> getDashBoard() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getParameter("token");

        if (!StringUtils.isEmpty(token)) {
            String[] split = token.split("\\|");

            long userId = Long.parseLong(split[2]);
            User user = userService.getUser(userId);

            Map<String, Object> map  = loginService.handleDashBoard(user);

            return new ResponseEntity<>(Utility.populateViewModel(
                    Utility.SUCCESS_STATUS_CODE, Arrays.asList("Successful"), map),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(Utility.populateViewModel(Utility.ERROR_STATUS_CODE, Arrays.asList("unauthorized access!!!")),
                HttpStatus.BAD_REQUEST);
    }
}
