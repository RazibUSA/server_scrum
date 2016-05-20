package com.mum.scrum.restController;

import com.mum.scrum.model.UserStory;
import com.mum.scrum.model.UserStoryStatus;
import com.mum.scrum.service.FormValidatorService;
import com.mum.scrum.service.UserStoryService;
import com.mum.scrum.utility.Utility;
import com.mum.scrum.viewmodel.ViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 984609, 984724 on 4/12/2016.
 */
@RestController
public class UserStoryController {

    @Autowired
    private UserStoryService userStoryService;

    @Autowired
    private FormValidatorService formValidatorService;

    @RequestMapping(value = "/userstory/{userstoryId}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    public ResponseEntity<ViewModel> getUser(@PathVariable("userstoryId") long userstoryId) {


        List<String> validations = userStoryService.validateUserStoryLoad(userstoryId);
        if (hasAnyLogicalError(validations)) {
            return new ResponseEntity<>(Utility.populateViewModel(Utility.ERROR_STATUS_CODE, validations), HttpStatus.BAD_REQUEST);
        }
        Map<String, Object> dataMap = userStoryService.handleGetUserStory(userstoryId);
        return new ResponseEntity<>(
                Utility.populateViewModel(Utility.SUCCESS_STATUS_CODE, Arrays.asList("Successful"), dataMap),
                HttpStatus.OK);

    }


    @RequestMapping(value = "/userstory", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
    public ResponseEntity<ViewModel> createUserStory(@Valid @RequestBody UserStory userStory, BindingResult bindResult) {

        //form validation
        if (bindResult.hasErrors()) {
            List<String> errorList = formValidatorService.doFormValidation(bindResult);
            return new ResponseEntity<>(Utility.populateViewModel(Utility.ERROR_STATUS_CODE, errorList), HttpStatus.BAD_REQUEST);
        }

        List<String> validations = userStoryService.validateUserStoryCreation(userStory);
        if (hasAnyLogicalError(validations)) {
            return new ResponseEntity<>(Utility.populateViewModel(Utility.ERROR_STATUS_CODE, validations), HttpStatus.BAD_REQUEST);
        }

        //add default role..
        if (userStory.getUserStoryStatus() == null) {
            userStory.setUserStoryStatus(new UserStoryStatus(1));
        }
        userStoryService.persist(userStory);

        //TODO to uri
        return new ResponseEntity<>(
                Utility.populateViewModel(Utility.SUCCESS_STATUS_CODE, Arrays.asList("UserStory has been created successfully!"),
                        userStoryService.handleGetUserStory(userStory.getId())),
                HttpStatus.CREATED);

    }

    @ResponseBody
    @RequestMapping(value = "/userstory/{userstoryId}", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.PUT)
    public ResponseEntity<ViewModel> updateUserStory(@PathVariable("userstoryId") long userStoryId, @Valid @RequestBody UserStory userStory, BindingResult bindResult) {
        //form validation
        if (bindResult.hasErrors()) {
            List<String> errorList = formValidatorService.doFormValidation(bindResult);
            return new ResponseEntity<>(Utility.populateViewModel(Utility.ERROR_STATUS_CODE, errorList), HttpStatus.BAD_REQUEST);
        }

        List<String> validations = userStoryService.validateUserStoryUpdate(userStory);
        if (hasAnyLogicalError(validations)) {
            return new ResponseEntity<>(Utility.populateViewModel(Utility.ERROR_STATUS_CODE, validations), HttpStatus.BAD_REQUEST);
        }
        userStoryService.updateUserStory(userStoryId, userStory);
        //TODO to uri
        return new ResponseEntity<>(
                Utility.populateViewModel(Utility.SUCCESS_STATUS_CODE, Arrays.asList("UserStory has been updated successfully!"),
                        userStoryService.handleGetUserStory(userStoryId)),
                HttpStatus.OK);

    }

    private boolean hasAnyLogicalError(List<String> validationList) {
        return (validationList != null && validationList.size() > 0);
    }
}
