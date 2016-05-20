package com.mum.scrum.service;

import com.mum.scrum.model.Project;

import java.util.List;
import java.util.Map;

/**
 * Created by 984609 on 4/12/2016.
 */
public interface ProjectService {
    void persist(Project project);
    Project getProject(long projectId);
    List<Project> getProjectsByProductOwner(long userId);
    List<Project> getProjectsByScrumMaster(long userId);
    List<Project> getProjectsByDeveloper(long userId);
    void updateProject(long projectId, Project project);
    List<String> validateProjectCreation(Project project);
    List<String> validateProjectUpdate(Project project);
    List<String> validateProjectLoad(long projectId);
    Map<String, Object> handleGetProject(long projectId);

}
