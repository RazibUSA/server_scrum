package com.mum.scrum.dao;

import com.mum.scrum.model.Sprint;

import java.util.List;

/**
 * Created by 984609 on 4/12/2016.
 */
public interface SprintDao {
    void persist(Sprint sprint);
    Sprint getSprint(long sprintId);
    List<Sprint> getAllSprints(long projectId);
    List<Integer> getTotalLogTimeByDays(long sprintId);
    int getTotalEstimation(long sprintId);

}
