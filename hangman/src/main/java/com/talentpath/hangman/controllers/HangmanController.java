package com.talentpath.hangman.controllers;

import com.talentpath.hangman.exceptions.InvalidIdException;
import com.talentpath.hangman.models.HangmanBoard;
import com.talentpath.hangman.models.HangmanGuess;
import com.talentpath.hangman.services.HangmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// hangman
// 		start a single game
//			pick a random word
//		get game state
//	     	get partial word (use '_' for unguessed letters) + number of moves remaining + letters guessed
//		guess the letter
//		guess the word
//		see all games (don't show hidden words for games that are underway)
//		cheat (show word without making a guess)

@RestController
@RequestMapping("/api")
public class HangmanController {

    @Autowired
    HangmanService service;

    @PostMapping("/begin")
    public Integer beginGame(){

        Integer gameId = service.beginGame();
        return gameId;
    }

    @GetMapping("/games")
    public List<HangmanBoard> getAllGames(){
        throw new UnsupportedOperationException();
    }

    @GetMapping("/gamestate/{gameId}")
    public HangmanBoard getGame(@PathVariable Integer gameId){
        try {
            return service.getGameById( gameId );
        } catch (InvalidIdException e) {
            return null;
        }
    }

    @GetMapping("/cheat/{gameId}")
    public String cheat( @PathVariable Integer gameId ){
        throw new UnsupportedOperationException();
    }

    //guessing a letter
    @PutMapping( "/guessletter")
    public HangmanBoard makeLetterGuess( @RequestBody HangmanGuess userGuess  ){
        throw new UnsupportedOperationException();
    }

    @PutMapping( "/guessword" )
    public HangmanBoard makeWordGuess( @RequestBody HangmanGuess userGuess ){
        throw new UnsupportedOperationException();
    }

}
