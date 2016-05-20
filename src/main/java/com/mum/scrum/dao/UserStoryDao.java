package com.mum.scrum.dao;

import com.mum.scrum.model.UserStory;

/**
 * Created by 984609 on 4/12/2016.
 */
public interface UserStoryDao {

    void persist(UserStory userStory);
    UserStory getUserStory(long userStoryId);

}
