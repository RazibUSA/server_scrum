package com.mum.scrum.service;

import com.mum.scrum.dao.SprintDao;
import com.mum.scrum.dao.UserStoryDao;
import com.mum.scrum.model.LogTime;
import com.mum.scrum.model.Project;
import com.mum.scrum.model.Sprint;
import com.mum.scrum.model.UserStory;
import com.mum.scrum.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 984609 on 4/12/2016.
 */
@Service("userStoryService")
@Transactional
public class UserStoryServiceImpl implements UserStoryService {

    private DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

    @Autowired
    private UserStoryDao userStoryDao;

    @Autowired
    private SprintDao sprintDao;

    @Override
    public void persist(UserStory userStory) {
        userStoryDao.persist(userStory);

        if (userStory.getSprints() != null) {

            for (Sprint sprintObj : userStory.getSprints()) {
                Sprint sprintPersistObj = sprintDao.getSprint(sprintObj.getId());
                if (sprintPersistObj.getUserStories() == null) {
                    sprintPersistObj.setUserStories(new ArrayList<UserStory>());
                }

                sprintPersistObj.getUserStories().add(userStory);
                sprintDao.persist(sprintPersistObj);

            }
        }
    }

    @Override
    public List<String> validateUserStoryCreation(UserStory userStory) {
        return validatePermission("canCreateUserStory");
    }

    @Override
    public List<String> validateUserStoryUpdate(UserStory userStory) {
        return validatePermission("canUpdateUserStory");
    }

    @Override
    public List<String> validateUserStoryLoad(long userStoryId) {
        return validatePermission("canViewUserStory");
    }

    @Override
    public Map<String, Object> handleGetUserStory(long userstoryId) {
        Map<String, Object> map = new HashMap<>();
        UserStory userStory = userStoryDao.getUserStory(userstoryId);
        map.put("userStoryList", Arrays.asList(userStory));
        map.put("sprintList", populateSprintsOfUserStory(userStory));
        map.put("logTimeList", populateLogTimesOfUserStory(userStory));
        return map;
    }

    @Override
    public void updateUserStory(long userStoryId, UserStory userStory) {
        UserStory userStoryObj = userStoryDao.getUserStory(userStoryId);
        userStoryObj.setTitle(userStory.getTitle());
        userStoryObj.setDescription(userStory.getDescription());
        userStoryObj.setEstimation(userStory.getEstimation());
        userStoryObj.setUser(userStory.getUser());
        if (userStory.getProject() != null) {
            userStoryObj.setProject(new Project(userStory.getProject().getId()));
        }

        if (userStory.getLogTimes() != null ) {
            if (userStoryObj.getLogTimes() == null) {
                userStoryObj.setLogTimes(new ArrayList<LogTime>());
            }
            userStoryObj.getLogTimes().clear();
            userStoryObj.getLogTimes().addAll(userStory.getLogTimes());
        }

        userStoryDao.persist(userStoryObj);

     /*   if (userStory.getSprints() != null) { //TODO save everything from one command

            for (Sprint sprintObj : userStory.getSprints()) {
                Sprint sprintPersistObj = sprintDao.getSprint(sprintObj.getId());
                if (sprintPersistObj.getUserStories() == null) {
                    sprintPersistObj.setUserStories(new ArrayList<UserStory>());
                }

                sprintPersistObj.getUserStories().clear();
                sprintPersistObj.getUserStories().add(userStoryObj);
                sprintDao.persist(sprintPersistObj);

            }
        }*/


    }

    private List<String> validatePermission(String permission) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getParameter("token");

        if (!StringUtils.isEmpty(token)) {
            String[] split = token.split("\\|");
            if (!Utility.hasPermission(permission, Integer.valueOf(split[1]))) {
                return Arrays.asList("Unauthorized access!!!");
            }
        }
        return Arrays.asList();

    }

    ///do not add this in the interface
    private List<Sprint> populateSprintsOfUserStory(UserStory userStory) {
        List<Sprint> sprints = userStory.getSprints();
        List<Sprint> newSprintList = new ArrayList<>();

        for (Sprint sprint : sprints) {
            Sprint sprintObj = new Sprint();
            sprintObj.setProject(sprint.getProject());
            sprintObj.setEndDate(sprint.getEndDate());
            sprintObj.setStartDate(sprint.getStartDate());
            sprintObj.setName(sprint.getName());

            newSprintList.add(sprintObj);
        }

        return newSprintList;
    }

    private List<LogTime> populateLogTimesOfUserStory(UserStory userStory) {
        List<LogTime> logTimes = userStory.getLogTimes();
        List<LogTime> newLogTimeList = new ArrayList<>();

        for (LogTime logTime : logTimes) {
            LogTime logTimeObj = new LogTime();
            logTimeObj.setLockedTime(logTime.getLockedTime());
            logTimeObj.setAssignedDate(logTime.getAssignedDate());
            //logTimeObj.setAssignedDateStr(dateFormat.format(logTime.getAssignedDate()));
            newLogTimeList.add(logTimeObj);
        }

        return newLogTimeList;
    }
}
