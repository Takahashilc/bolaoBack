package com.bolaoworldcup.api.dto.group;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.bolaoworldcup.api.entities.Group;

import java.io.Serializable;

public class GroupDTO implements Serializable {

    @Size(min = 1, max = 1, message = "Name must have 1 character")
    @NotBlank(message = "Required field")
    private String name;

    public GroupDTO() {
    }

    public GroupDTO(Group entity) {
        this.name = entity.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
