package com.mwl.util;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class GrammarTest {
    Grammar grammar;

    @Before
    public void setUp() {
        grammar = new Grammar();
    }

    @Test
    public void generate_Sentence() {
        System.out.println(grammar.generate_Sentence());
    }

    @Test
    public void fixGrammar_replacesCommasWithSpaces_withoutSpaces() {
        String result = grammar.fixGrammar("There was , a dog.");
        assertEquals("There was, a dog.", result);
    }

    @Test
    public void fixGrammar_replacesCommasWithoutSpaces_staySame() {
        String result = grammar.fixGrammar("There was, a dog.");
        assertEquals("There was, a dog.", result);
    }

    @Test
    public void fixGrammar_aChangedToAn_withVowelsAfter() {
        String result = grammar.fixGrammar("a elephant sang");
        assertEquals("an elephant sang", result);
    }

    @Test
    public void fixGrammar_aNotChanged_WithoutVowelsAfter() {
        String result = grammar.fixGrammar("an elephant sang");
        assertEquals("an elephant sang", result);
    }

    @Test
    public void fixGrammar_changesNothingWithRightInput() {
        String result = grammar.fixGrammar("an ungrateful, wise elephant sang");
        assertEquals("an ungrateful, wise elephant sang", result);
    }

    @Test
    public void fixGrammar_MultipleChangesWorks() {
        String result = grammar.fixGrammar("a ungrateful , stupid elephant and a unfair , lame, balding , angry toad");
        assertEquals("an ungrateful, stupid elephant and an unfair, lame, balding, angry toad", result);
    }
}