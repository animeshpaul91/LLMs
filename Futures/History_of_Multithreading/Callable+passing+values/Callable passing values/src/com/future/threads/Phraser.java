package com.future.threads;

import java.util.concurrent.Callable;


public class Phraser implements Callable <String> {
    private final String word1;
    private final String word2;

    public Phraser(String word1, String word2){
        this.word1 = word1;
        this.word2 = word2;
    }
    
    @Override
    public String call() throws InterruptedException {
        return  word1 + word2;
    }
}