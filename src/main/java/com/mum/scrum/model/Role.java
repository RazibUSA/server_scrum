package com.mum.scrum.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: nadim
 * Date: 4/5/16
 * Time: 8:22 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {
    private long id;
    private String name;
///use enum


    public Role() {
    }

    public Role(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @Column(name = "id")
  //  @GeneratedValue(strategy = GenerationType.AUTO)
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
}
