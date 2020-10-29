package com.talentpath.hangman.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( code= HttpStatus.NOT_ACCEPTABLE, reason="Cannot add guess to completed game.")
public class GameOverException extends Exception {
    public GameOverException( String message ){
        super( message );
    }

    public GameOverException( String message, Throwable inner ){
        super (message, inner);
    }
}
