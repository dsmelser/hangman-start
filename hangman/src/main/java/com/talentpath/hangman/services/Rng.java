package com.talentpath.hangman.services;

import java.util.Random;

//Singleton pattern
public class Rng {
    static Random generator = new Random();

    public static int nextInt( int exclusiveMax ){
        return generator.nextInt( exclusiveMax );
    }

}
