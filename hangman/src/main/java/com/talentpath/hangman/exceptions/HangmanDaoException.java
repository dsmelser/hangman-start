package com.talentpath.hangman.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( code = HttpStatus.INTERNAL_SERVER_ERROR,
        reason = "Error processing request. Please try again.")
public class HangmanDaoException extends Exception {
    public HangmanDaoException( String message ){
        super( message );
    }

    public HangmanDaoException( String message, Throwable innerException ){
        super( message, innerException );
    }
}
