package com.mum.scrum.service;

import com.mum.scrum.model.UserStory;

import java.util.List;
import java.util.Map;

/**
 * Created by 984609 on 4/12/2016.
 */
public interface UserStoryService {
    List<String> validateUserStoryCreation(UserStory userStory);
    List<String> validateUserStoryUpdate(UserStory userStory);
    List<String> validateUserStoryLoad(long userStoryId);
    Map<String, Object> handleGetUserStory(long userstoryId);
    void persist(UserStory userStory);
    void updateUserStory(long userStoryId, UserStory userStory);
}
