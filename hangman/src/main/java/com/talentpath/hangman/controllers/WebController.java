package com.talentpath.hangman.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    //when they just go to our server
    //url, nothing after the slash
    //give them the index.html page
    @GetMapping( "" )
    public String homePage(){
        return "index";
    }

}
