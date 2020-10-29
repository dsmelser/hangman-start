package com.talentpath.hangman.services;

import com.talentpath.hangman.daos.HangmanDao;
import com.talentpath.hangman.daos.InMemHangmanDao;
import com.talentpath.hangman.exceptions.InvalidIdException;
import com.talentpath.hangman.models.HangmanBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles( "servicetesting" )
class HangmanServiceTest {

    //@Autowired
    HangmanService serviceToTest;

    @Autowired
    HangmanDao dao;

    @BeforeEach
    void setUp() {
        serviceToTest = new HangmanService( dao );
        dao.reset();
    }

    @Test
    void beginGame() {

        //what are the expectations after we start a game?
        //shouldn't get -1
        //should return a valid game id (not totally sure how to check this yet)
        //          should be able to find the game in the database
        //total # of games should increase by one

        //3 A's of unit testing
        //Arrange
        List<HangmanBoard> gamesBeforeStart = serviceToTest.getGames();

        //Act
        Integer gameId = serviceToTest.beginGame();

        //Assert
        try {
            HangmanBoard matchingGame = serviceToTest.getGameById(gameId);
            assertEquals( gameId, matchingGame.getGameId() );

            List<HangmanBoard> gamesAfterStart = serviceToTest.getGames();

            assertEquals( gamesBeforeStart.size() + 1, gamesAfterStart.size() );

        } catch ( InvalidIdException ex ){
            fail( "Hit exception during golden path test: " + ex.getMessage());
        }

    }

    @Test
    void getGames(){
        //Arrange
        // nothing to do for this one?
        //Act
        List<HangmanBoard> allGames = serviceToTest.getGames();
        //Assert
        //TODO: add testing dao so we know how many games to expect
        assertEquals( 3, allGames.size() );

        HangmanBoard board0 = allGames.get(0);

        assertEquals( 1, board0.getGameId() );
        assertEquals( 3, board0.getMovesRemaining());
        assertEquals( 0 ,board0.getGuessedLetters().size());
        assertEquals( "___", board0.getPartial() );

        HangmanBoard board1 = allGames.get(1);

        assertEquals( 2, board1.getGameId() );
        assertEquals( 2, board1.getMovesRemaining());
        assertEquals( 2,board1.getGuessedLetters().size());
        assertTrue( board1.getGuessedLetters().contains( "a" ));
        assertTrue( board1.getGuessedLetters().contains( "b" ));
        assertEquals( "___a__", board1.getPartial() );

        HangmanBoard board2 = allGames.get(2);

        assertEquals( 3, board2.getGameId() );
        assertEquals( 1, board2.getMovesRemaining());
        assertEquals( 2 ,board2.getGuessedLetters().size());
        assertTrue( board2.getGuessedLetters().contains( "z" ));
        assertTrue( board2.getGuessedLetters().contains( "r" ));
        assertEquals( "z____r", board2.getPartial() );
    }

    @Test
    void getById(){
        //three A's
        //Arrange

        //Act
        try {
            HangmanBoard retrievedGame = serviceToTest.getGameById(1);

            //Assert
            assertEquals( 1, retrievedGame.getGameId() );
            assertEquals( 3, retrievedGame.getMovesRemaining());
            assertEquals( 0 ,retrievedGame.getGuessedLetters().size());
            assertEquals( "___", retrievedGame.getPartial() );

        } catch( InvalidIdException ex ){
            fail( "Hit exception during golden path test: " + ex.getMessage());
        }
    }

    @Test
    void getByIdInvalidId(){
        //three A's
        //Arrange

        //Act
        try {
            HangmanBoard retrievedGame = serviceToTest.getGameById(-1);

            fail( "Didn't throw an exception when trying to get game -1" );

        } catch( InvalidIdException ex ){

        }
    }

    @Test
    void enterGuess(){

    }

    @Test
    void enterGuessNullGuessObject(){

    }

    @Test
    void enterGuessNullId(){

    }

    @Test
    void enterGuessFakeId(){

    }

    @Test
    void enterGuessNullGuessLetter(){

    }

    @Test
    void enterGuessTooLong(){

    }

    @Test
    void enterGuessTooShort(){

    }

    @Test
    void enterGuessInvalidCharacters(){

    }


    @Test
    void guessWord(){

    }

    @Test
    void guessWordNullGuessObject(){

    }

    @Test
    void guessWordNullGameId(){

    }

    @Test
    void guessWordFakeGameId(){


    }

    @Test
    void guessWordNullWord(){

    }

    @Test
    void guessWordTooShort(){

    }

    @Test
    void guessWordTooLong(){

    }

    @Test
    void guessWordInvalidCharacters(){

    }
}