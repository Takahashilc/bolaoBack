package com.bolaoworldcup.api.dto.log;

import javax.validation.constraints.NotNull;

import com.bolaoworldcup.api.entities.Log;
import com.bolaoworldcup.api.entities.enums.Action;

public class LogInputDTO extends LogDTO {

    @NotNull(message = "Required field")
    private Long user;

    @NotNull(message = "Required field")
    private Action action;

    public LogInputDTO() {
    }

    public LogInputDTO(Long user, Action action) {
        this.user = user;
        this.action = action;
    }

    public LogInputDTO(Log entity) {
        this.user = entity.getUser().getId();
        this.action = entity.getAction();
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
