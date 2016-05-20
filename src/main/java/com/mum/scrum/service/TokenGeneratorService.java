package com.mum.scrum.service;

import com.mum.scrum.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: nadim
 * Date: 4/8/16
 * Time: 9:31 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TokenGeneratorService {

    public String generateToken(User user);
   // public boolean authenticateToken(String token);



}
