package com.talentpath.hangman.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( code= HttpStatus.INTERNAL_SERVER_ERROR, reason = "Got unexpected null.")
public class NullArgumentException extends  Exception {

    public NullArgumentException( String message ){
        super(message);
    }

    public NullArgumentException( String message, Throwable inner){
        super(message, inner);
    }
}
