package com.bolaoworldcup.api.dto.tip;

import java.io.Serializable;

import com.bolaoworldcup.api.entities.User;

public class TipUserOutputDTO implements Serializable {

    private Long id;
    private String nickname;

    public TipUserOutputDTO() {
    }

    public TipUserOutputDTO(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public TipUserOutputDTO(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
