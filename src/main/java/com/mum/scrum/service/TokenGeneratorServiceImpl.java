package com.mum.scrum.service;

import com.mum.scrum.model.User;
import com.mum.scrum.viewmodel.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: nadim
 * Date: 4/8/16
 * Time: 9:32 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("tokenGeneratorService")
public class TokenGeneratorServiceImpl implements TokenGeneratorService {

    @Autowired
    Environment environment;

    @Override
    public String generateToken(User user) {
        Token token = new Token((int) user.getRole().getId(), getExpirationTimeValue(),
                environment.getProperty("token.secret.key"), user.getId());
        return token.generateToken();
    }

//    @Override
//    public boolean authenticateToken(String realToken) {
//        String[] split = realToken.split("|");
//        long timestamp = Long.valueOf(split[0]);
//        int role = Integer.valueOf(split[1]);
//
////        if (role != user.getRole().getId()) {
////            return false;
////        }
//
//        if (System.currentTimeMillis() > timestamp) {
//            return false;
//        }
//
//        Token token = new Token(role, timestamp, environment.getProperty("token.secret.key"));
//
//        if (!token.compareTokenSignature(realToken)) {
//            return false;
//        }
//        return true;
//    }

    private long getExpirationTimeValue() {
        return System.currentTimeMillis() + Long.valueOf(environment.getProperty("token.expiration.threshold"));
    }
}
