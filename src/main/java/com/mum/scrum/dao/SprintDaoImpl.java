package com.mum.scrum.dao;

import com.mum.scrum.model.Sprint;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 984609 on 4/12/2016.
 */
@Repository
public class SprintDaoImpl implements SprintDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void persist(Sprint sprint) {
        em.persist(sprint);
    }

    @Override
    public Sprint getSprint(long sprintId) {
        return em.find(Sprint.class, sprintId);
    }

    @Override
    public List<Sprint> getAllSprints(long projectId) {
        Query query = em.createQuery("SELECT s FROM Sprint s where s.project.id = :projectId")
                .setParameter("projectId", projectId);
        return (List<Sprint>) query.getResultList();
    }

    @Override
    public List<Integer> getTotalLogTimeByDays(long sprintId) {
        String sql = " select sum(T.lockedsum) from  sprints_stories ss,"
                + " ("
                + " select uslt.user_story_id as uid, lt.assigned_date_str as assigneddate, sum(lt.locked_time) as lockedsum"
                + " from log_time lt, user_story_log_time uslt"
                + " where uslt.logTimes_id = lt.id"
                + " group by uslt.user_story_id, lt.assigned_date_str"
                + " ) T"
                + " where T.uid = ss.user_story_id and ss.sprint_id =  "
                + sprintId
                + " group by T.assigneddate order by T.assigneddate asc";

        Query q = em.createNativeQuery(sql);
        List<Integer> times = q.getResultList();

        if (times != null && times.size() > 0) {
            return times;
        }

        return new ArrayList<>();
    }

    @Override
    public int getTotalEstimation(long sprintId) {
        String sql = " select sum(u.estimation)  from user_story u, sprints_stories ss " +
                " where u.id = ss.user_story_id and ss.sprint_id = "
                + sprintId;
        Query q = em.createNativeQuery(sql);
        int value = (int) q.getSingleResult();
        return value;


    }
}
