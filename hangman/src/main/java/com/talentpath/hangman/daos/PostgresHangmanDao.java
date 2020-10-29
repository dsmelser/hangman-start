package com.talentpath.hangman.daos;

import com.talentpath.hangman.exceptions.HangmanDaoException;
import com.talentpath.hangman.exceptions.InvalidIdException;
import com.talentpath.hangman.models.HangmanGame;
import com.talentpath.hangman.models.HangmanGuess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
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

    //normal JDBC query:
    //create connection     -- automatic (will use connection info from application.properties)
    //create statement      -- automatic
    //execute statement     -- automatic
    //loop over resultset until we get a false  --automatic
    //process each row into data   -- we handle this in the template with rowmappers


    @Autowired
    private JdbcTemplate template;

    @Override
    public List<HangmanGame> getAllGames() {
        return template.query( "SELECT * FROM \"Games\"", new GameMapper() );
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


//    public HangmanGame addGuess(HangmanGuess toAdd){
//        int rowsAffected = template.update( "UPDATE Games SET TotalGuesses = ?, RemainingGuesses = ? WHERE gameId = " + toAdd.getGameId() );
//    }

    @Override
    public HangmanGame getGameById(Integer gameId) throws InvalidIdException {


        //queryForObject
        //expected result is we'll process exactly one row
        //if we get zero or >1 this throws an exception
        try {
            HangmanGame retrievedGame = template.queryForObject("SELECT * FROM \"Games\" WHERE \"gameId\" = '" + gameId + "'",
                    new GameMapper());
            return retrievedGame;
        } catch (DataAccessException ex){
            //translate this exception
            //TODO: split out getting no rows vs the database being broken and having non-unique ids
            throw new InvalidIdException("Error retrieving game id: " + gameId, ex);
        }
    }

    @Override
    public List<String> getLettersForGame(Integer gameId) {

        return template.query(
                "SELECT \"letter\" FROM \"LettersGuessed\" WHERE \"gameId\" = '"+gameId+"' ",
                new LetterMapper());
    }

    @Override
    public void addLetterGuess(HangmanGuess userGuess) throws HangmanDaoException {
        try {
            int rowsAffected = template.update(
                    "INSERT INTO \"LettersGuessed\" (\"gameId\", \"letter\") VALUES ('"
                            + userGuess.getGameId() + "', '"
                            + userGuess.getGuess() + "')");

            //TODO: add sanity check/thrown exceptions if rowsAffected != 1
            //0 means the insert just failed probably
            //>1 means the sky is falling
        } catch (  DataAccessException ex ){
            // "translate" the exception by catching the specific
            // exception type we DONT want the rest of the system
            // to know about and throwing a more general exception
            // that doesn't pollute our interface

            throw new HangmanDaoException( "Error while attempting to add a guessed letter: " + ex.getMessage(), ex );
        }
    }

    @Override
    public void editGame(HangmanGame currentGame) throws HangmanDaoException {

        try {
            int rowsAffected = template.update(
                    "UPDATE \"Games\" SET \"totalGuesses\" = '"+currentGame.getTotalGuesses()
                            +"', \"remainingGuesses\" = '"+currentGame.getRemainingGuesses()+"' " +
                            "WHERE \"gameId\" = '"+currentGame.getGameId()+"';");

            //TODO: add sanity check/thrown exceptions if rowsAffected != 1
            //0 means the insert just failed probably
            //>1 means the sky is falling
        } catch (  DataAccessException ex ){
            // "translate" the exception by catching the specific
            // exception type we DONT want the rest of the system
            // to know about and throwing a more general exception
            // that doesn't pollute our interface

            throw new HangmanDaoException( "Error while attempting to update game: " + ex.getMessage(), ex );
        }

    }


    @Override
    public void reset() {

        template.update("TRUNCATE \"PossibleWords\", \"LettersGuessed\", \"Games\"  RESTART IDENTITY");

        //template.update("DELETE FROM \"PossibleWords\"");
        template.update( "INSERT INTO \"PossibleWords\" (\"word\") VALUES ('zebra'),('giraffe')");
    }

    class LetterMapper implements  RowMapper<String> {
        @Override
        public String mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getString( "letter");
        }
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
