package com.mwl.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Grammar {

    private Map<String, List<String>> grammar;

    public Grammar() {
        // default constructor
        grammar = new HashMap<>();
        readRules();
    }

    /**
     * Read in a grammar from a file. All non-terminals (e.g. keys in grammar dictionary) are on the left side of "::=".
     * Every option for each non-terminal is separated by a "|".
     */
    private void readRules() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("resources/grammar/dungeon_grammar.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rules = line.split("::=");
                if (rules.length > 1) {
                    String nonterm = rules[0].strip();
                    List<String> terms = Arrays.stream(rules[1].split("[|]"))
                            .map(String::strip).collect(Collectors.toList());
                    addTo(nonterm, terms);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds the given key and list of values to grammar dictionary, if not already present. Throws IllegalArgumentException
     * if true.
     *
     * @param key
     * @param values
     */
    private void addTo(String key, List<String> values) throws IllegalArgumentException {
        if (grammar.containsKey(key)) {
            throw new IllegalArgumentException("Redundant non-terminal! :: " + key);
        }
        grammar.put(key, values);
    }

    /**
     * Generate a random sentence from the built grammar and return as a String. Throws an IllegalArgumentException if
     * grammar built does not have a "<sentence>" non-terminal.
     *
     * @return
     * @throws IllegalArgumentException
     */
    public String generate_Sentence() throws IllegalArgumentException {
        if (!grammar.containsKey("<sentence>")) {
            throw new IllegalArgumentException("Grammar is missing sentence non-terminal!");
        }
        return capitalize(fixGrammar(generate("<sentence>"))) + ".";
    }

    /**
     * Capitalize the first character in the given string
     *
     * @param s
     * @return
     */
    private String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * Removes white spaces before commas and replaces "a" with "an" when next character is a vowel.
     *
     * @param sentence
     * @return
     */
    String fixGrammar(String sentence) {
        return sentence.replaceAll(" ,", ",")
                .replaceAll("a ([a,e,i,o,u])", "an $1");
    }

    /**
     * recursively choose a random value for each non-terminal, returning string that is made by joining each terminal
     * string with white spaces between them.
     *
     * @param symbol
     * @return
     */
    private String generate(String symbol) {
        List<String> values = grammar.get(symbol);
        String value = values.get(ThreadLocalRandom.current().nextInt(values.size()));
        return Arrays.stream(value.split(" "))
                .map(e -> {
                    if (grammar.containsKey(e)) {
                        return generate(e);
                    } else {
                        return e;
                    }
                })
                .collect(Collectors.joining(" "));
    }
}
