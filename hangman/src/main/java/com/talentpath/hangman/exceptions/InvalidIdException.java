package com.talentpath.hangman.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( code = HttpStatus.NOT_FOUND, reason = "Could not find game with matching ID" )
public class InvalidIdException extends Exception {

    Integer attemptedId;

    public Integer getAttemptedId() {
        return attemptedId;
    }


    public InvalidIdException( String message ){
        super( message );
    }

    public InvalidIdException( String message, Throwable innerException ){
        super( message, innerException );
    }

}
