package com.bolaoworldcup.api.dto.team;

import java.io.Serializable;

import com.bolaoworldcup.api.entities.Team;

public class TeamGroupDTO implements Serializable {

    private Long id;
    private String name;

    public TeamGroupDTO() {
    }

    public TeamGroupDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TeamGroupDTO(Team entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
