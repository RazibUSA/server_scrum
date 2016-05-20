package com.mum.scrum.viewmodel;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by 984609 on 4/8/2016.
 */
public class Login {

    @NotEmpty
    private String email;
    @NotEmpty
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
