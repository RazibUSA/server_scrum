package com.mum.scrum.viewmodel;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

/**
 * Created with IntelliJ IDEA.
 * User: nadim
 * Date: 4/8/16
 * Time: 9:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class Token {
    private int role;
    private long userId;
    private long timestamp;
    private String secretKey;


    public Token(int role, long timestamp, String secretKey, long userId) {
        this.role = role;
        this.timestamp = timestamp;
        this.secretKey = secretKey;
        this.userId = userId;
    }

    public Token(String realToken, String secretKey) {

        String[] split = realToken.split("|");
        this.timestamp = Long.valueOf(split[0]);
        this.role = Integer.valueOf(split[1]);
        this.userId = Long.valueOf(split[2]);
        this.secretKey = secretKey;


    }

    public String generateSignature() {

        String tokenWithSecretKey = this.toString() + "|" + secretKey;
        return Hashing.md5().hashString(tokenWithSecretKey, Charsets.UTF_8).toString();
    }

    public String generateToken() {
        return this.toString() + "|" + generateSignature();
    }

    public boolean compareTokenSignature(String realToken) {

        if (realToken.equals(generateToken())) {
            return true;
        }
        return false;

    }

    @Override
    public String toString() {
        return timestamp + "|" + role  + "|" +  userId;
    }
}
