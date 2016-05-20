package com.mum.scrum.model;

import com.mum.scrum.config.CustomDateDeserializer;
import com.mum.scrum.config.CustomDateSerializer;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nadim
 * Date: 4/5/16
 * Time: 8:15 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "project")
public class Project extends Persistent implements Serializable {
    private long id;
    @NotEmpty(message = "Project name is required.")
    private String name;

    private Date startDate;
    private Date endDate;
    List<Sprint> sprints;
//    private List<User> users;

    private User owner;
    private User managedBy;

    public Project() {
    }

    public Project(long id) {
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

    @OneToMany(cascade = CascadeType.ALL,  orphanRemoval = true)
    @JsonIgnore
    public List<Sprint> getSprints() {
        return sprints;
    }

    @JsonProperty
    public void setSprints(List<Sprint> sprints) {
        this.sprints = sprints;
    }


//    @ElementCollection
//    @CollectionTable(name="project_users", joinColumns=@JoinColumn(name="project_id"))
//    public List<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(List<User> users) {
//        this.users = users;
//    }

    @ManyToOne
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @ManyToOne
    @JoinColumn(name = "managed_by_id")
    public User getManagedBy() {
        return managedBy;
    }

    public void setManagedBy(User managedBy) {
        this.managedBy = managedBy;
    }
}
