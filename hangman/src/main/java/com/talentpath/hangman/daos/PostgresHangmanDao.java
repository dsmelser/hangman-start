package com.talentpath.hangman.daos;

import com.talentpath.hangman.models.HangmanGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Component
@Profile( "production" )
public class PostgresHangmanDao implements HangmanDao {

    @Autowired
    JdbcTemplate

    @Override
    public List<HangmanGame> getAllGames() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getAllWords() {
        throw new UnsupportedOperationException();
    }

    @Override
    public HangmanGame addGame(HangmanGame toAdd) {
        throw new UnsupportedOperationException();
    }

    @Override
    public HangmanGame getGameById(Integer gameId) {
        throw new UnsupportedOperationException();
    }
}
