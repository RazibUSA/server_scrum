package com.mum.scrum.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nadim
 * Date: 4/11/16
 * Time: 1:49 AM
 * To change this template use File | Settings | File Templates.
 */
@Service("formValidatorService")
public class FormValidatorServiceImpl implements FormValidatorService {

    @Override
    public List<String> doFormValidation(BindingResult bindingResult) {
        List<FieldError> errors = bindingResult.getFieldErrors();
        List<String> errorList = new ArrayList<>();
        for (FieldError error : errors) {
            errorList.add(error.getDefaultMessage());
        }

//        if (StringUtils.isEmpty(user.getPassword())) {
//            errorList.add("password is empty.");
//        }
        return errorList;
    }
}
