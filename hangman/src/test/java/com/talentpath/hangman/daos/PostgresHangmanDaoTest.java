package com.talentpath.hangman.daos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

        //String sql = "DELETE FROM \"Words\"";
        daoToTest.resetAllTables();

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
    void addGame() {
    }

    @Test
    void getGameById() {
    }
}