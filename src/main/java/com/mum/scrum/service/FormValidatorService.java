package com.mum.scrum.service;

import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nadim
 * Date: 4/11/16
 * Time: 1:49 AM
 * To change this template use File | Settings | File Templates.
 */
public interface FormValidatorService {

    List<String> doFormValidation(BindingResult bindingResult);
}
