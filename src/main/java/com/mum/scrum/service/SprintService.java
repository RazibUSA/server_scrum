package com.mum.scrum.service;

import com.mum.scrum.model.Sprint;

import java.util.List;
import java.util.Map;

/**
 * Created by 984609 on 4/12/2016.
 */
public interface SprintService {
    void persist(Sprint sprint);
    Sprint getSprint(long sprintId);
    List<String> validateSprintLoad(long sprintId);
    List<String> validateSprintCreation(Sprint sprint);
    List<String> validateSprintUpdate(Sprint sprint);
    void updateSprint(long sprintId, Sprint sprint);
    List<Sprint> getAllSprints(long projectId);
    Map<String, Object> handleGetSprint(long sprintId);
    Map<String, Object> reportGeneraor(long sprintId);

}
