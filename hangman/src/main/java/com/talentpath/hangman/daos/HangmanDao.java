package com.talentpath.hangman.daos;

import com.talentpath.hangman.exceptions.HangmanDaoException;
import com.talentpath.hangman.exceptions.InvalidIdException;
import com.talentpath.hangman.models.HangmanGame;
import com.talentpath.hangman.models.HangmanGuess;

import java.util.List;

public interface HangmanDao {
    List<HangmanGame> getAllGames();

    List<String> getAllWords();

    HangmanGame addGame(HangmanGame toAdd);

    HangmanGame getGameById(Integer gameId) throws InvalidIdException;

    void reset();

    List<String> getLettersForGame(Integer gameId);

    void addLetterGuess(HangmanGuess userGuess) throws HangmanDaoException;

    void editGame(HangmanGame currentGame) throws HangmanDaoException;
}
