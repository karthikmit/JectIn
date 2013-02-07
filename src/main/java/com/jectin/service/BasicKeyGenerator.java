package com.jectin.service;

import java.util.Random;

public class BasicKeyGenerator {

    private Random randGenerator = null;
    private KeyAlphabetSystem keyAlphabetSystem = new KeyAlphabetSystem();

    public BasicKeyGenerator(long seed) {
        randGenerator = new Random(seed);
    }

    public String getNextKey(int length) {
        String key = "";
        if (length <= 0) {
            return key;
        }

        for (int i = 0; i < length; i++) {
            key += nextKeyElement();
        }
        return key;
    }

    private String nextKeyElement() {
        int index = nextKeyElementIndex();
        return keyAlphabetSystem.getAlphabetAt(index);
    }

    private int nextKeyElementIndex() {

        int index = randGenerator.nextInt(keyAlphabetSystem.getNumberOfAlphabets());
        return index;
    }
}
