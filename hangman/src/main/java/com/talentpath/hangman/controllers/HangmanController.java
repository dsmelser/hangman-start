package com.talentpath.hangman.controllers;

import com.talentpath.hangman.exceptions.GameOverException;
import com.talentpath.hangman.exceptions.HangmanDaoException;
import com.talentpath.hangman.exceptions.InvalidIdException;
import com.talentpath.hangman.exceptions.NullArgumentException;
import com.talentpath.hangman.models.HangmanBoard;
import com.talentpath.hangman.models.HangmanGuess;
import com.talentpath.hangman.services.HangmanService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Integer beginGame() throws NullArgumentException {
        return service.beginGame();
    }

    @GetMapping("/games")
    public List<HangmanBoard> getAllGames(){
        return service.getGames();
    }

    @GetMapping("/gamestate/{gameId}")
    public HangmanBoard getGame(@PathVariable Integer gameId) throws InvalidIdException {
        return service.getGameById(gameId);
    }

    @GetMapping("/cheat/{gameId}")
    public String cheat( @PathVariable Integer gameId ) throws InvalidIdException {
        return service.cheat( gameId );
    }

    //guessing a letter
    @PutMapping( "/guessletter")
    public HangmanBoard makeLetterGuess( @RequestBody HangmanGuess userGuess  ) throws InvalidIdException, HangmanDaoException{
            return service.enterGuess(userGuess);

    }

    @PutMapping( "/guessword" )
    public HangmanBoard makeWordGuess( @RequestBody HangmanGuess userGuess ) throws InvalidIdException, HangmanDaoException, GameOverException {
        return service.guessWord( userGuess );
    }

}
