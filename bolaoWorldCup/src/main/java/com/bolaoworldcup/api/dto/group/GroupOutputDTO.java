package com.bolaoworldcup.api.dto.group;

import javax.validation.constraints.Size;

import com.bolaoworldcup.api.dto.team.TeamGroupDTO;
import com.bolaoworldcup.api.entities.Group;
import com.bolaoworldcup.api.entities.Team;

import java.util.HashSet;
import java.util.Set;

public class GroupOutputDTO extends GroupDTO {

    private Long id;

    private Set<TeamGroupDTO> teams = new HashSet<>();

    public GroupOutputDTO() {
    }

    public GroupOutputDTO(Group entity) {
        super(entity);
        this.id = entity.getId();
        entity.getTeams().forEach(team -> this.teams.add(new TeamGroupDTO(team)));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<TeamGroupDTO> getTeams() {
        return teams;
    }
}
