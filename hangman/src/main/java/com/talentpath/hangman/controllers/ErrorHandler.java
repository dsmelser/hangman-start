package com.talentpath.hangman.controllers;


import com.talentpath.hangman.exceptions.InvalidIdException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ErrorHandler {

//    @ExceptionHandler( value = InvalidIdException.class)
//    @ResponseStatus(code = HttpStatus.NOT_FOUND)
//    public String errorMessageOnNotFound(InvalidIdException ex, WebRequest request){
//        return "something bad happened: " + ex.getMessage();
//    }


}
