package com.jectin.service;

import java.util.ArrayList;
import java.util.List;

public class KeyAlphabetSystem {
    private int numberOfAlphabets = 0;
    private List<String> alphabets = new ArrayList<String>();

    public KeyAlphabetSystem() {
        initializeAlphabets();
    }

    private void initializeAlphabets() {
        for (int i = 0; i < 256; i++) {
            char ci = (char) i;

            if (Character.isLetterOrDigit(ci) == true) {
                String asciiChar = Character.toString(ci);
                boolean hasNonAlpha = asciiChar.matches("^.*[^a-zA-Z0-9 ].*$");
                if (hasNonAlpha == false)
                    alphabets.add(asciiChar);
            }
            numberOfAlphabets = alphabets.size();
        }

    }

    public int getNumberOfAlphabets() {
        return numberOfAlphabets;
    }

    public String getAlphabetAt(int index) {
        if (index <= 0) {
            return alphabets.get(0);
        }
        if (index >= numberOfAlphabets) {
            return alphabets.get(numberOfAlphabets - 1);
        }

        return alphabets.get(index);
    }
}
