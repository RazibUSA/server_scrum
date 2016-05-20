package com.mum.scrum.viewmodel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: nadim
 * Date: 4/6/16
 * Time: 5:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class PermissionModel {

    public static Map<Integer, String[]> permissionMap = new HashMap<>();
    static {
        permissionMap.put(1, getSystemAdminPermission());
        permissionMap.put(2, getProductOwnerPermission());
        permissionMap.put(3, getScrumMasterPermission());
        permissionMap.put(4, getDeveloperPermission());
    }

    private static String[] SYS_ADMIN_PERMISSION = new String[] {"canViewUser", "canCreateUser", "canDeleteUser", "canUpdateUser"};
    private static String[] PRODUCT_OWNER_PERMISSION = new String[] {"canViewProject", "canCreateProject", "canDeleteProject",
            "canUpdateProject", "canCreateUserStory", "canUpdateUserStory", "canDeleteUserStory"};
    private static String[] SCRUM_MASTER_PERMISSION = new String[] {"canViewUser", "canViewSprint", "canCreateSprint", "canDeleteSprint",
            "canUpdateSprint", "canUpdateProject"};
    private static String[] DEVELOPER_PERMISSION = new String[] {"canViewUser", "canViewSprint", "canUpdateUserStory", "canViewUserStory",
            "canUpdateSprint"};

    public static String[] getSystemAdminPermission() {
        return SYS_ADMIN_PERMISSION;
    }
    public static String[] getProductOwnerPermission() {
        return PRODUCT_OWNER_PERMISSION;
    }
    public static String[] getScrumMasterPermission() {
        return SCRUM_MASTER_PERMISSION;
    }
    public static String[] getDeveloperPermission() {
        return DEVELOPER_PERMISSION;
    }




}
