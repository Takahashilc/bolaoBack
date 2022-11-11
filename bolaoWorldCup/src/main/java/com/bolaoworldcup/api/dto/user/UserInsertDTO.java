package com.bolaoworldcup.api.dto.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.bolaoworldcup.api.services.validation.UserInsertValid;

@UserInsertValid
public class UserInsertDTO extends UserDTO {

    @Size(min = 8, message = "Password must have at least 8 characters")
    @NotBlank(message = "Required field")
    private String password;

    public UserInsertDTO() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
