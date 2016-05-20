package com.mum.scrum.model;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

@MappedSuperclass
public class Persistent implements Serializable {
    private static final long serialVersionUID = 1L;

    @Version
    @JsonIgnore
    long version;

    @Version
    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
