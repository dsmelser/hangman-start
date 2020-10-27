package com.talentpath.hangman.services;

import com.talentpath.hangman.daos.HangmanDao;
import com.talentpath.hangman.daos.InMemHangmanDao;
import com.talentpath.hangman.exceptions.InvalidIdException;
import com.talentpath.hangman.models.HangmanBoard;
import com.talentpath.hangman.models.HangmanGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HangmanService {

    HangmanDao dao;

    @Autowired
    public HangmanService( HangmanDao dao ){
        this.dao = dao;
    }

    public Integer beginGame() {
        //what are the rules for starting a game of hangman
        //need to pick a random word
        //give 5 guesses by default
        //TODO: pick a more fun guess number

        List<String> possibleWords = dao.getAllWords();

        String secretWord = possibleWords.get( Rng.nextInt(possibleWords.size()));

        HangmanGame toAdd = new HangmanGame();
        toAdd.setSecretWord( secretWord );
        toAdd.setRemainingGuesses(5);
        toAdd.setTotalGuesses(0);
        toAdd.setGuessedLetters( new ArrayList<>());

        toAdd = dao.addGame( toAdd );

        return toAdd.getGameId();
    }

    public List<HangmanBoard> getGames(){
        List<HangmanGame> allGames = dao.getAllGames();

        return allGames.stream().map( g -> convertGame(g) ).collect(Collectors.toList());
    }

    public HangmanBoard getGameById(Integer gameId) throws InvalidIdException {

        HangmanGame game = dao.getGameById( gameId );

        if( game == null ) throw new InvalidIdException( "Could not find game with id = " + gameId );

        return convertGame( game );
    }

    private HangmanBoard convertGame( HangmanGame toConvert ){

        HangmanBoard converted = new HangmanBoard();
        converted.setGameId( toConvert.getGameId() );
        converted.setGuessedLetters( toConvert.getGuessedLetters() );
        converted.setMovesRemaining( toConvert.getRemainingGuesses() );

        String partial = "";
        for( char checkLetter : toConvert.getSecretWord().toCharArray() ){
            //TODO: check this
            partial = partial + (toConvert.getGuessedLetters().stream().anyMatch( l -> l.charAt(0) == checkLetter )
                ? checkLetter : '_');
        }

        converted.setPartial( partial );

        return converted;
    }
}
