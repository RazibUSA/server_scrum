package com.mum.scrum.utility;

import com.mum.scrum.viewmodel.PermissionModel;
import com.mum.scrum.viewmodel.Token;
import com.mum.scrum.viewmodel.ViewModel;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: nadim
 * Date: 4/9/16
 * Time: 1:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class Utility {
    public static int SUCCESS_STATUS_CODE = 1;
    public static int ERROR_STATUS_CODE = 0;
    public static int NOT_LOGGED_IN_STATUS_CODE = -1;
    public static boolean authenticateToken(String realToken, String secretKey) {

        if (StringUtils.isEmpty(realToken)) {
            return false;
        }
        String[] split = realToken.split("\\|");
        long timestamp = Long.valueOf(split[0]);
        int role = Integer.valueOf(split[1]);
        long userId = Long.valueOf(split[2]);
        if (System.currentTimeMillis() > timestamp) {
            return false;
        }

        Token token = new Token(role, timestamp, secretKey, userId);

        if (!token.compareTokenSignature(realToken)) {
            return false;
        }
        return true;
    }

    public static boolean hasPermission(String permission, int role) {
         if (Arrays.asList(PermissionModel.permissionMap.get(role)).contains(permission)) {
             return true;
         }
        return false;
    }

    public static ViewModel populateViewModel(int statusCode, List<String> statusMessageList, Map<String, Object> data) {
        ViewModel viewModel = new ViewModel();
        viewModel.setDataMap(data);
        viewModel.setStatusCode(statusCode);
        viewModel.setStatusMessage(statusMessageList);
        return viewModel;
    }

    public static ViewModel populateViewModel(int statusCode, List<String> statusMessageList) {
        return populateViewModel(statusCode, statusMessageList, new HashMap<String, Object>());
    }


}
