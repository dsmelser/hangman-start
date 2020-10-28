package com.talentpath.hangman.daos;

import com.talentpath.hangman.models.HangmanGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

        List<Integer> insertedIds = template.query(
                "INSERT INTO \"Games\" (\"secretWord\", \"totalGuesses\", \"remainingGuesses\") " +
                "VALUES ('"+toAdd.getSecretWord()+"', '"+toAdd.getTotalGuesses()+"', '"+toAdd.getRemainingGuesses()+"') returning \"gameId\";", new IdMapper() );




        toAdd.setGameId( insertedIds.get(0));

        return toAdd;
    }

    @Override
    public HangmanGame getGameById(Integer gameId) {

        HangmanGame retrievedGame = template.queryForObject( "SELECT * FROM \"Games\" WHERE \"gameId\" = '"+gameId+"'",
                new GameMapper());
        return retrievedGame;
    }

    @Override
    public List<String> getLettersForGame(Integer gameId) {
        //TODO: actually implement and test
        return new ArrayList<>();
    }


    @Override
    public void reset() {
        template.update("TRUNCATE \"PossibleWords\", \"LettersGuessed\", \"Games\"  RESTART IDENTITY");

        //template.update("DELETE FROM \"PossibleWords\"");
        template.update( "INSERT INTO \"PossibleWords\" (\"word\") VALUES ('zebra'),('giraffe')");
    }


    class WordMapper implements RowMapper<String> {

        @Override
        public String mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
            return resultSet.getString("word");
        }
    }

    class IdMapper implements  RowMapper<Integer> {

        @Override
        public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getInt("gameId");
        }
    }

    class GameMapper implements  RowMapper<HangmanGame> {

        @Override
        public HangmanGame mapRow(ResultSet resultSet, int i) throws SQLException {
            HangmanGame toReturn = new HangmanGame();
            toReturn.setSecretWord( resultSet.getString("secretWord") );
            toReturn.setTotalGuesses( resultSet.getInt( "totalGuesses" ));
            toReturn.setRemainingGuesses( resultSet.getInt( "remainingGuesses"));
            //can't do this in here
            //toReturn.setGuessedLetters();
            toReturn.setGameId( resultSet.getInt("gameId"));

            return toReturn;
        }
    }


}
