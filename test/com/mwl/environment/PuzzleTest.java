package com.mwl.environment;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class PuzzleTest {
    Puzzle puzzle;

    @Before
    public void setUp() {
        puzzle = new Puzzle("This is the question", "easy",
                "Correct Answer", Set.of("wrong1", "wrong2", "wrong3"));
    }

    @Test
    public void getAllAnswers() {
        var correct = List.of("Correct Answer", "wrong1", "wrong2", "wrong3");
        puzzle.getAllAnswers().forEach(e -> assertTrue(correct.contains(e)));
    }

    @Test
    public void getDifficultyInt_easy() {
        assertEquals(1, puzzle.getDifficultyInt());
    }

    @Test
    public void getDifficultyInt_medium() {
        puzzle = new Puzzle("", "medium", "", Set.of(""));
        assertEquals(2, puzzle.getDifficultyInt());
    }

    @Test
    public void getDifficultyInt_hard() {
        puzzle = new Puzzle("", "hard", "", Set.of(""));
        assertEquals(3, puzzle.getDifficultyInt());
    }

    @Test
    public void getDifficultyInt_weirdCase() {
        puzzle = new Puzzle("", "hArD", "", Set.of());
        assertEquals(3, puzzle.getDifficultyInt());
    }
}