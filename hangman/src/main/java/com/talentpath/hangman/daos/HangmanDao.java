package com.talentpath.hangman.daos;

import com.talentpath.hangman.models.HangmanGame;

import java.util.List;

public interface HangmanDao {
    List<HangmanGame> getAllGames();

    List<String> getAllWords();

    HangmanGame addGame(HangmanGame toAdd);

    HangmanGame getGameById(Integer gameId);

    void reset();

    List<String> getLettersForGame(Integer gameId);
}
