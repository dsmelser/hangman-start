package com.talentpath.hangman.daos;

import com.talentpath.hangman.models.HangmanGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@Profile( {"production", "daotesting"} )
public class PostgresHangmanDao implements HangmanDao {

    @Autowired
    private JdbcTemplate template;

    @Override
    public List<HangmanGame> getAllGames() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getAllWords() {

        List<String> allWords = template.query( "SELECT * FROM \"PossibleWords\"",
                new WordMapper());

        return allWords;
    }

    @Override
    public HangmanGame addGame(HangmanGame toAdd) {
        throw new UnsupportedOperationException();
    }

    @Override
    public HangmanGame getGameById(Integer gameId) {
        throw new UnsupportedOperationException();
    }

    //only used for unit testing
    public void resetAllTables(){
        template.update("DELETE FROM \"PossibleWords\"");
        template.update( "INSERT INTO \"PossibleWords\" (\"word\") VALUES ('zebra'),('giraffe')");
    }

    class WordMapper implements RowMapper<String> {

        @Override
        public String mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
            return resultSet.getString("word");
        }
    }


}
