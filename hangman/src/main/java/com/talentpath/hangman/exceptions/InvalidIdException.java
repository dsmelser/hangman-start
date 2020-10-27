package com.talentpath.hangman.exceptions;

public class InvalidIdException extends Exception {

    public InvalidIdException( String message){
        super( message );
    }

    public InvalidIdException( String message, Throwable innerException ){
        super( message, innerException );
    }

}
