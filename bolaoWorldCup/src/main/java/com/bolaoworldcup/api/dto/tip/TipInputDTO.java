package com.bolaoworldcup.api.dto.tip;

import javax.validation.constraints.NotNull;

import com.bolaoworldcup.api.entities.Tip;
import com.bolaoworldcup.api.entities.enums.Result;

public class TipInputDTO extends TipDTO {

    @NotNull(message = "Required field")
    private Long match;

    @NotNull(message = "Required field")
    private Result result;

    public TipInputDTO() {
    }

    public TipInputDTO(Long match, Result result) {
        this.match = match;
        this.result = result;
    }

    public TipInputDTO(Tip entity) {
        match = entity.getMatch().getId();
        result = entity.getResult();
    }

    public Long getMatch() {
        return match;
    }

    public void setMatch(Long match) {
        this.match = match;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
