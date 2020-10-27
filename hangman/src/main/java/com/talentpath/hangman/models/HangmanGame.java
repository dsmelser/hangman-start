package com.talentpath.hangman.models;

import java.util.List;
import java.util.stream.Collectors;

public class HangmanGame {
    private Integer gameId;
    private String secretWord;
    private Integer totalGuesses;
    private Integer remainingGuesses;
    private List<String> guessedLetters;

    public HangmanGame(){

    }

    public HangmanGame( HangmanGame that ){
        this.gameId = that.gameId;
        this.secretWord = that.secretWord;
        this.totalGuesses = that.totalGuesses;
        this.remainingGuesses = that.remainingGuesses;
        this.guessedLetters = that.guessedLetters.stream().collect(Collectors.toList());
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getSecretWord() {
        return secretWord;
    }

    public void setSecretWord(String secretWord) {
        this.secretWord = secretWord;
    }

    public Integer getTotalGuesses() {
        return totalGuesses;
    }

    public void setTotalGuesses(Integer totalGuesses) {
        this.totalGuesses = totalGuesses;
    }

    public Integer getRemainingGuesses() {
        return remainingGuesses;
    }

    public void setRemainingGuesses(Integer remainingGuesses) {
        this.remainingGuesses = remainingGuesses;
    }

    public List<String> getGuessedLetters() {
        return guessedLetters;
    }

    public void setGuessedLetters(List<String> guessedLetters) {
        this.guessedLetters = guessedLetters;
    }



}
