package com.mum.scrum.service;

import com.mum.scrum.model.User;
import com.mum.scrum.viewmodel.Login;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: nadim
 * Date: 4/8/16
 * Time: 9:34 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class DashBoardLoaderFactory {

    public Map<String, Object> loadDashboard(User user) {

        DashBoard dashBoard = resolvedDashboard(user);
        return dashBoard.populateData(user);

    }

    abstract DashBoard resolvedDashboard(User user);

}
