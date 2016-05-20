package com.mum.scrum.model;

import com.mum.scrum.config.CustomDateDeserializer;
import com.mum.scrum.config.CustomDateSerializer;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nadim
 * Date: 4/5/16
 * Time: 8:20 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "sprint")
public class Sprint extends Persistent implements Serializable {

    private long id;
    @NotEmpty(message = "Sprint name is required.")
    private String name;
    private Date startDate;
    private Date endDate;
    @NotNull(message = "Project id required")
    private Project project;
    private List<UserStory> userStories = new ArrayList<>();

    public Sprint() {
    }

    public Sprint(long id) {
        this.id = id;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @Column(name = "start_date")
    public Date getStartDate() {
        return startDate;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @Column(name = "end_date")
    public Date getEndDate() {
        return endDate;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="sprints_stories",
            joinColumns = @JoinColumn( name="sprint_id"),
            inverseJoinColumns = @JoinColumn( name="user_story_id")
    )
    public List<UserStory> getUserStories() {
        return userStories;
    }

    @JsonProperty
    public void setUserStories(List<UserStory> userStories) {
        this.userStories = userStories;
    }

    @ManyToOne
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
