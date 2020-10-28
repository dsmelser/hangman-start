package com.talentpath.hangman.daos;

import com.talentpath.hangman.models.HangmanGame;
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

        HangmanGame validationGame = daoToTest.getGameById(returnedGame.getGameId());
        assertEquals( 1, validationGame.getGameId() );
        assertEquals( 0, validationGame.getTotalGuesses());
        assertEquals( "xyz", validationGame.getSecretWord());
        assertEquals( 5, validationGame.getRemainingGuesses() );
        assertEquals( 0, validationGame.getGuessedLetters().size());
    }

    @Test
    void getGameById() {
    }
}