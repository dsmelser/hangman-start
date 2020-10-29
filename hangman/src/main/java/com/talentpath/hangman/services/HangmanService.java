package com.talentpath.hangman.services;

import com.talentpath.hangman.daos.HangmanDao;
import com.talentpath.hangman.daos.InMemHangmanDao;
import com.talentpath.hangman.exceptions.GameOverException;
import com.talentpath.hangman.exceptions.HangmanDaoException;
import com.talentpath.hangman.exceptions.InvalidIdException;
import com.talentpath.hangman.models.HangmanBoard;
import com.talentpath.hangman.models.HangmanGame;
import com.talentpath.hangman.models.HangmanGuess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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

        allGames.stream().forEach( g -> g.setGuessedLetters(dao.getLettersForGame(g.getGameId())) );

        return allGames.stream().map( g -> convertGame(g) ).collect(Collectors.toList());
    }

    public HangmanBoard getGameById(Integer gameId) throws InvalidIdException {

        HangmanGame game = dao.getGameById( gameId );

        List<String> guessedLetters = dao.getLettersForGame( gameId );

        game.setGuessedLetters( guessedLetters );

        return convertGame( game );
    }

    public HangmanBoard enterGuess(HangmanGuess userGuess) throws InvalidIdException, HangmanDaoException {
        HangmanGame currentGame = dao.getGameById(userGuess.getGameId());
        currentGame.setGuessedLetters(dao.getLettersForGame(userGuess.getGameId()));

        String guess = userGuess.getGuess();
        String secretWord = currentGame.getSecretWord();

        if( !currentGame.getGuessedLetters().contains( userGuess.getGuess() ) ) {
            dao.addLetterGuess(userGuess);
        }

        if( !secretWord.contains(guess) || currentGame.getGuessedLetters().contains( userGuess.getGuess() ) ) {
            currentGame.setRemainingGuesses( currentGame.getRemainingGuesses() - 1 );
        }

        currentGame.setTotalGuesses( currentGame.getTotalGuesses() + 1);

        dao.editGame( currentGame );

        return getGameById( userGuess.getGameId() );

    }

    public HangmanBoard guessWord(HangmanGuess userGuess)
            throws InvalidIdException, HangmanDaoException, GameOverException {

        HangmanGame currentGame = dao.getGameById(userGuess.getGameId());

        if( currentGame.getRemainingGuesses() < 1 ){
            throw new GameOverException( "Tried to guess for completed game with id = " + userGuess.getGameId());
        }

        currentGame.setGuessedLetters(dao.getLettersForGame(userGuess.getGameId()));

        Set<String> unguessedLetters = new HashSet<>();

        String secretWord = currentGame.getSecretWord();
        for( Character c : secretWord.toCharArray()){
            unguessedLetters.add( ""+c );
        }

        unguessedLetters.removeAll(currentGame.getGuessedLetters());

        if( userGuess.getGuess().equals(currentGame.getSecretWord())){

            for( String unguessedLetter : unguessedLetters ){
                HangmanGuess individualLetterGuess = new HangmanGuess();
                individualLetterGuess.setGameId(userGuess.getGameId());
                individualLetterGuess.setGuess( unguessedLetter );
                dao.addLetterGuess( individualLetterGuess );
            }

        } else {
            currentGame.setTotalGuesses( currentGame.getTotalGuesses() + 1);
            currentGame.setRemainingGuesses( currentGame.getRemainingGuesses() -1);
        }

        dao.editGame( currentGame );

        return getGameById( userGuess.getGameId() );

    }

    public String cheat(Integer gameId) throws InvalidIdException {

        return dao.getGameById( gameId).getSecretWord();

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
