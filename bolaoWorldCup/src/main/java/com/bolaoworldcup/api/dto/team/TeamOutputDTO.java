package com.bolaoworldcup.api.dto.team;

import com.bolaoworldcup.api.dto.group.GroupDTO;
import com.bolaoworldcup.api.entities.Team;

public class TeamOutputDTO extends TeamDTO {

    private Long id;

    private GroupDTO group;

    public TeamOutputDTO() {
    }

    public TeamOutputDTO(Long id, String name, String acronym, String flagUrl, GroupDTO group) {
        super(name, acronym, flagUrl);
        this.id = id;
        this.group = group;
    }

    public TeamOutputDTO(Team entity) {
        super(entity);
        this.id = entity.getId();
        this.group = new GroupDTO(entity.getGroup());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GroupDTO getGroup() {
        return group;
    }

    public void setGroup(GroupDTO group) {
        this.group = group;
    }
}
