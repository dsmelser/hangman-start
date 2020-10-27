package com.talentpath.hangman.models;

import java.util.List;

public class HangmanBoard {
    Integer gameId;
    String partial;
    Integer movesRemaining;
    List<String> guessedLetters;


    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getPartial() {
        return partial;
    }

    public void setPartial(String partial) {
        this.partial = partial;
    }

    public Integer getMovesRemaining() {
        return movesRemaining;
    }

    public void setMovesRemaining(Integer movesRemaining) {
        this.movesRemaining = movesRemaining;
    }

    public List<String> getGuessedLetters() {
        return guessedLetters;
    }

    public void setGuessedLetters(List<String> guessedLetters) {
        this.guessedLetters = guessedLetters;
    }

}
