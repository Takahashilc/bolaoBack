package com.bolaoworldcup.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.bolaoworldcup.api.entities.Role;

import java.io.Serializable;

public class RoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @Size(min = 6, max = 30, message = "Authority must be between 6 and 30 characters")
    @NotBlank(message = "Required field")
    private String authority;

    public RoleDTO() {
    }

    public RoleDTO(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    public RoleDTO(Role entity) {
        id = entity.getId();
        authority = entity.getAuthority();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
