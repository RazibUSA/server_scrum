package com.mum.scrum.service;

import com.mum.scrum.dao.SprintDao;
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

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by 984609 on 4/12/2016.
 */
@Service("sprintService")
public class SprintServiceImpl implements SprintService {

    @Autowired
    private SprintDao sprintDao;

    @Override
    @Transactional
    public void persist(Sprint sprint) {
        sprintDao.persist(sprint);
    }

    @Override
    @Transactional
    public Sprint getSprint(long sprintId) {
        return sprintDao.getSprint(sprintId);
    }

    @Override
    public List<String> validateSprintCreation(Sprint sprint) {
        return validatePermission("canCreateSprint");
    }

    @Override
    public List<String> validateSprintLoad(long sprintId) {
        return validatePermission("canViewSprint");
    }

    @Override
    public List<String> validateSprintUpdate(Sprint sprint) {
        return validatePermission("canUpdateSprint");
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

    @Override
    @Transactional
    public void updateSprint(long sprintId, Sprint sprint) {
        Sprint sprintObj = sprintDao.getSprint(sprintId);
        sprintObj.setName(sprint.getName());

        if (sprint.getProject() != null) {
            sprintObj.setProject(new Project(sprint.getProject().getId()));
        }
        if (sprint.getUserStories() != null) {
            if (sprintObj.getUserStories() != null) {
                sprintObj.getUserStories().clear();
            }
            sprintObj.getUserStories().addAll(sprint.getUserStories());
        }
        sprintDao.persist(sprintObj);

    }

    @Override
    @Transactional
    public List<Sprint> getAllSprints(long projectId) {
        return sprintDao.getAllSprints(projectId);
    }

    @Override
    @Transactional
    public Map<String, Object> handleGetSprint(long sprintId) {
        Map<String, Object> map = new HashMap<>();
        Sprint sprint = sprintDao.getSprint(sprintId);
        map.put("sprintList", Arrays.asList(sprint));
        map.put("userStoryList", getRefinedUserStoryList(sprint));
        return map;
    }

    private List<UserStory> getRefinedUserStoryList(Sprint sprint) {

        List<UserStory> userStories = new ArrayList<>();

        if (sprint.getUserStories() != null && sprint.getUserStories().size() > 0) {
            for (UserStory userStory : sprint.getUserStories()) {

                UserStory userStoryObj = new UserStory();
                userStoryObj.setId(userStory.getId());
                userStoryObj.setUser(userStory.getUser());
                userStoryObj.setTitle(userStory.getTitle());
                userStoryObj.setProject(userStory.getProject());
                userStoryObj.setEstimation(userStory.getEstimation());
                userStoryObj.setDescription(userStory.getDescription());
                userStoryObj.setUserStoryStatus(userStory.getUserStoryStatus());

                userStories.add(userStoryObj);
            }
        }
        return userStories;
    }

    @Override
    public Map<String, Object> reportGeneraor(long sprintId) {
        return reportGeneratorImpl(sprintId);
    }

    @Transactional(rollbackFor = Exception.class) //this may be bug of sqlite
    private Map<String, Object> reportGeneratorImpl(long sprintId) {

        Map<String, Object> map = new HashMap<>();
        try {
            map.put("compleatedHours", sprintDao.getTotalLogTimeByDays(sprintId));
            map.put("totalEstimatedHours", sprintDao.getTotalEstimation(sprintId));
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;

    }


}
