package com.mum.scrum.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by 984609 on 4/14/2016.
 */
@Entity
@Table(name = "user_story_status")
public class UserStoryStatus {
    public static long TODO = 1;
    public static long IN_PROGRESS = 2;
    public static long DONE = 3;
    public static long STOPPED = 3;
    private long id;
    private String status;

    public UserStoryStatus() {
    }

    public UserStoryStatus(long id) {
        this.id = id;
    }

    public UserStoryStatus(long id, String status) {
        this.id = id;
        this.status = status;
    }

    @Id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
