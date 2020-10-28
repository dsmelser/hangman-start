package com.talentpath.hangman.daos;

// testing dao

import com.talentpath.hangman.models.HangmanGame;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//what data are we tracking for hangman???
//game
//  gameId
//  totalGuesses
//  remainingGuesses
//  secretWord
//  usedLetters
@Component
@Profile( "servicetesting" )
public class InMemHangmanDao implements HangmanDao {

    List<HangmanGame> allGames = new ArrayList<>();
    List<String> possibleWords = new ArrayList<>();

    public InMemHangmanDao(){
        reset();
    }

    @Override
    public void reset() {
        possibleWords.clear();
        allGames.clear();

        possibleWords.add("cat");
        possibleWords.add("island");
        possibleWords.add("zephyr");


        HangmanGame easy = new HangmanGame();
        easy.setGameId(1);
        easy.setSecretWord("cat");
        easy.setGuessedLetters( new ArrayList<>() );
        easy.setTotalGuesses(0);
        easy.setRemainingGuesses(3);

        allGames.add( easy );


        HangmanGame medium = new HangmanGame();
        medium.setGameId(2);
        medium.setSecretWord("island");
        medium.setGuessedLetters(Arrays.asList( "a", "b" ) );
        medium.setTotalGuesses(2);
        medium.setRemainingGuesses(2);

        allGames.add( medium );

        HangmanGame hard = new HangmanGame();
        hard.setGameId(3);
        hard.setSecretWord("zephyr");
        hard.setGuessedLetters(Arrays.asList( "r", "z" ) );
        hard.setTotalGuesses(2);
        hard.setRemainingGuesses(1);

        allGames.add( hard );
    }

    @Override
    public List<String> getLettersForGame(Integer gameId) {
        //TODO: actually implement and test
        return new ArrayList<>();
    }

    @Override
    public List<HangmanGame> getAllGames() {
        return allGames.stream().map( toCopy -> new HangmanGame( toCopy) ).collect(Collectors.toList());
    }

    @Override
    public List<String> getAllWords() {
        return possibleWords.stream().collect(Collectors.toList());
    }

    @Override
    public HangmanGame addGame(HangmanGame toAdd) {
        HangmanGame realCopy = new HangmanGame(toAdd);
        realCopy.setGameId( allGames.stream().mapToInt( g -> g.getGameId() ).max().orElse(0) + 1 );
        allGames.add( realCopy );
        return new HangmanGame(realCopy);
    }

    @Override
    public HangmanGame getGameById(Integer gameId) {
        HangmanGame toCopy = allGames.stream().filter( g -> g.getGameId() == gameId ).findAny().orElse(null);
        if( toCopy != null ) return new HangmanGame(toCopy);
        return null;
    }


}
