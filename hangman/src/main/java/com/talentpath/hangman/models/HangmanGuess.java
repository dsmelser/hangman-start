package com.talentpath.hangman.models;

//this will both an id and the letter being guessed
public class HangmanGuess {

    Integer gameId;
    String guess;

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getGuess() {
        return guess.toLowerCase();
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }
}
