package com.mwl.environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Puzzle {
    private String question;
    private String answer;
    private Set<String> wrong_answers;
    private String difficulty;

    /**
     * Constructor
     *
     * @param question
     * @param difficulty
     * @param answer
     * @param wrong_answers
     */
    public Puzzle(String question, String difficulty, String answer, Set<String> wrong_answers) {
        this.question = question;
        this.answer = answer;
        this.wrong_answers = wrong_answers;
        this.difficulty = difficulty.toLowerCase();
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public Set<String> getWrong_answers() {
        return wrong_answers;
    }

    /**
     * Returns all the answers to the question. The correct answer is somewhere randomly in the mixture.
     *
     * @return
     */
    public List<String> getAllAnswers() {
        List<String> temp = new ArrayList<>(wrong_answers);
        temp.add(ThreadLocalRandom.current().nextInt(temp.size() + 1), answer);
        return temp;
    }

    /**
     * Returns the difficulty of the question by mapping to an int.
     * Easy -> 1
     * Medium -> 2
     * Hard -> 3
     *
     * @return
     */
    public int getDifficultyInt() {
        int result = 1;
        switch (difficulty) {
            case "easy" -> result = 1;
            case "medium" -> result = 2;
            case "hard" -> result = 3;
        }
        return result;
    }
}
