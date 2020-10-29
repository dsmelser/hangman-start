package com.talentpath.hangman.daos;

import com.talentpath.hangman.exceptions.HangmanDaoException;
import com.talentpath.hangman.exceptions.InvalidIdException;
import com.talentpath.hangman.models.HangmanGame;
import com.talentpath.hangman.models.HangmanGuess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("daotesting")
class PostgresHangmanDaoTest {
//    List<HangmanGame> getAllGames();
//
//    List<String> getAllWords();
//
//    HangmanGame addGame(HangmanGame toAdd);
//
//    HangmanGame getGameById(Integer gameId) throws InvalidIdException;
//
//    void reset();
//
//    List<String> getLettersForGame(Integer gameId);
//
//    void addLetterGuess(HangmanGuess userGuess) throws HangmanDaoException;
//
//    void editGame(HangmanGame currentGame) throws HangmanDaoException;


    @Autowired
    PostgresHangmanDao daoToTest;

    @BeforeEach
    void setUp() {
        daoToTest.reset();
    }

    @Test
    void getAllGames() {
    }

    @Test
    void getAllWords() {

        List<String> allWords = daoToTest.getAllWords();
        assertEquals(2, allWords.size() );

        //a.compareTo(b)
        //  -   a < b, a before b
        //  0   a == b a and b are equivalent
        //  +   a > b  b before a
        allWords.sort( (a, b) -> a.compareToIgnoreCase(b) );

        assertEquals( "giraffe", allWords.get(0) );
        assertEquals( "zebra", allWords.get(1));
    }

    @Test
    void addGameAndGetById() {
        try {

                //Arrange
            HangmanGame toAdd = new HangmanGame();
            toAdd.setGuessedLetters(new ArrayList<>());
            toAdd.setTotalGuesses(0);
            toAdd.setRemainingGuesses(5);
            toAdd.setSecretWord("xyz");

            //Act
               HangmanGame returnedGame = daoToTest.addGame(toAdd);

            //Assert
            assertEquals( 1, returnedGame.getGameId() );
            assertEquals( 0, returnedGame.getTotalGuesses());
            assertEquals( "xyz", returnedGame.getSecretWord());
            assertEquals( 5, returnedGame.getRemainingGuesses() );
            assertEquals( 0, returnedGame.getGuessedLetters().size());

            HangmanGame validationGame = null;

                validationGame = daoToTest.getGameById(returnedGame.getGameId());

            assertEquals( 1, validationGame.getGameId() );
            assertEquals( 0, validationGame.getTotalGuesses());
            assertEquals( "xyz", validationGame.getSecretWord());
            assertEquals( 5, validationGame.getRemainingGuesses() );
            assertEquals( 0, validationGame.getGuessedLetters().size());
        } catch (InvalidIdException e) {
            fail( "produced invalid id exception during golden path test");
        }
    }

    @Test
    void addGameNullGameObject(){
        //test what happens if the entire HangmanGame object is nulll
    }

    @Test
    void addGameNullSecretWord(){
        //test what happens if the HangmanGame object looks normal except that the word is null
    }

    @Test
    void addGamesNullTotalGuesses(){

    }

    @Test
    void addGamesNullRemainingGuesses(){

    }

    @Test
    void getByIdBadId(){
        //test what happens if we try to look up a game with a fake id
    }

    @Test
    void getGameById() {
    }

    @Test
    void getLettersForGame(){}

    @Test
    void getLettersForGameNullId(){
        //check what happens if we send a null to this method
    }

    @Test
    void getLettersForGameFakeId(){
        //check what happens if we send a FAKE id to this method
    }

    @Test
    void addLetterGuess(){}

    @Test
    void addLetterGuessNullGuessObject(){
        //test what happens when we send this method a null
    }

    @Test
    void addLetterGuessNullId(){
        //test what happens with a normal guess object but the id is null (the other fields should NOT be)
    }

    @Test
    void addLetterGuessFakeId(){
        //as above but instead of a null id one that just doesn't match a game
    }

    @Test
    void addLetterGuessTooLong(){
        //test what happens if we send a guess that's more than one character
    }

    @Test
    void addLetterGuessTooShort(){
        //test what happens if we send a guess that's zero characters: ""
    }

    @Test
    void addLetterGuessNullWord(){

    }

    @Test
    void editGame(){}

    @Test
    void editGameNullGameObject(){

    }

    @Test
    void editGameNullId(){

    }

    @Test
    void editGameNullWord(){

    }

    @Test
    void editGameTotalNull(){

    }

    @Test
    void editGameRemainingNull(){

    }


}