package com.mum.scrum.dao;

import com.mum.scrum.model.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nadim
 * Date: 4/5/16
 * Time: 11:14 PM
 * To change this template use File | Settings | File Templates.
 */

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveJpaContact() {


//        User user = new User();
//        user.setEmail("ff");
//        user.setFirstName("ff1");
//        em.persist(user);
//
//        Project project = new Project();
//        project.setName("11");
//        project.setStartDate(new Date());
//        project.setEndDate(new Date());
//
//        List<User> users = new ArrayList<>();
//        users.add(user);
//        project.setUsers(users);
//
//        em.persist(project);
//        System.out.println(".......");

       Role role1 = new Role(1, "Sys admin");
       Role role2 = new Role(2, "Product Owner");
       Role role3 = new Role(3, "Scrum master");
       Role role4 = new Role(4, "Developer");

        em.persist(role1);
        em.persist(role2);
        em.persist(role3);
        em.persist(role4);

        UserStoryStatus userStoryStatus1 = new UserStoryStatus(1, "TODO");
        UserStoryStatus userStoryStatus2 = new UserStoryStatus(2, "INPROGRESS");
        UserStoryStatus userStoryStatus3 = new UserStoryStatus(3, "DONE");
        UserStoryStatus userStoryStatus4 = new UserStoryStatus(4, "STOPPED");

        em.persist(userStoryStatus1);
        em.persist(userStoryStatus2);
        em.persist(userStoryStatus3);
        em.persist(userStoryStatus4);


    }

    @Override
    public User getUser(long usrId) {
        return em.find(User.class, usrId);
    }

    @Override
    public User getUser(String email) {
        Query query = em.createQuery("SELECT u FROM User u where u.email = :email")
                .setParameter("email", email);
        List<User> resultList =  query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    @Override
    public List<User> getAllUsers() {
        Query query = em.createQuery("SELECT u FROM User u");
        return  query.getResultList();
    }

    @Override
    public void persistUser(User user) {
        em.persist(user);
    }

    @Override
    public void deleteUser(User user) { //TODO more works needed
        em.remove(user);
    }
}
