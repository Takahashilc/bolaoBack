package com.bolaoworldcup.api.services.exceptions;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String msg) {
        super(msg);
    }
}
