package com.mum.scrum.dao;

import com.mum.scrum.model.Sprint;
import com.mum.scrum.model.UserStory;
import com.mum.scrum.service.UserStoryService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 984609 on 4/12/2016.
 */
@Repository
public class UserStoryDaoImpl implements UserStoryDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void persist(UserStory userStory) {

        em.persist(userStory);

      /*  if (userStory.getSprints() != null) {

            for (Sprint sprintObj : userStory.getSprints()) {
                Sprint sprintPersistObj = em.find(Sprint.class, sprintObj.getId());
                if (sprintPersistObj.getUserStories() == null) {
                   sprintPersistObj.setUserStories(new ArrayList<UserStory>());
                }

                sprintPersistObj.getUserStories().add(userStory);
                em.persist(sprintPersistObj);

            }
        }*/
    }

    @Override
    public UserStory getUserStory(long userStoryId) {
        return em.find(UserStory.class, userStoryId);
    }
}
