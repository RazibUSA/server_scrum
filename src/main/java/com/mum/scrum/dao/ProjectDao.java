package com.mum.scrum.dao;

import com.mum.scrum.model.Project;
import com.mum.scrum.model.Sprint;
import com.mum.scrum.model.UserStory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nadim
 * Date: 4/6/16
 * Time: 5:00 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ProjectDao {

    void persistProject(Project project);
    Project getProject(long projectId);
    void deleteProject(Project project);
    List<Project> getAllProjects();
    List<Project> getProjectsByProductOwner(long userId);
    List<Project> getProjectsByScrumMaster(long userId);
    List<Project> getProjectsByDeveloper(long userId);
    List<Sprint> getAllSprints(long projectId);
    List<UserStory> getProductsBackLog();
    List<Project> getUserProjects(long userId);
    List<UserStory> getAllUserStories(long sprintId);
    List<UserStory> getAllTodoUserStory(long projectId);



}
