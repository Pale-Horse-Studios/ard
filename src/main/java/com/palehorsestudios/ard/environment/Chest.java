package com.palehorsestudios.ard.environment;

import com.palehorsestudios.ard.util.Codes;
import com.palehorsestudios.ard.util.ConsoleManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Chest {
    private List<Item> reward;
    private Puzzle puzzle;
    private boolean broken;

    public Chest(Puzzle puzzle) {
        this.puzzle = puzzle;
        reward = makeAward();
    }

    /**
     * Helper method to get a bunch of random items (not power stones) and put it into reward list.
     */
    private List<Item> makeAward() {
        List<Item> result = new ArrayList<>();
        int scale = puzzle.getDifficultyInt();
        int items = Item.values().length;
        for (int i = 0; i < ThreadLocalRandom.current().nextInt(scale, scale * 2); i++) {
            result.add(Item.values()[ThreadLocalRandom.current().nextInt(items - 6)]);
        }
        return result;
    }

    /**
     * Prints out the question and answers for the puzzle in the chest. If uyser chooses correctly, returns the reward
     * list, else returns an empty list.
     * <p>
     * NOTE: Once this method is called it sets broken to be true, and will not be able to interact with the chest again.
     *
     * @return
     */
    public String askQuestion() {
        StringBuilder chestQuestion = new StringBuilder();
        if (!broken) {
            chestQuestion.append("You approach a chest and a question appears...");
            chestQuestion.append("\n").append(puzzle.getQuestion());
            int index = 1;
            List<String> answers = puzzle.getAllAnswers();
            for (String answer : answers) {
                chestQuestion.append("\n").append(index).append(". ").append(answer);
                index += 1;
            }
        } else {
            chestQuestion.append("The chest does nothing");
        }
        broken = true;
        return chestQuestion.toString();
    }

    List<Item> evaluateAnswer(String answer) {
        List<Item> result = new ArrayList<>();
        if (puzzle.getAnswer().toLowerCase().equals(answer.toLowerCase())) {
            result = reward;
        }
        return result;
    }

    /**
     * Helper method to parse the inputs for given list of options.
     */
    private int getInput(List<List<String>> options) {
        boolean doLoop = true;
        int choice = -1;
        while (doLoop) {
            options.forEach(System.out::println); // print out the options
            String input = ConsoleManager.scanner().nextLine().strip(); // get the choice from console

            // map each item in options to be boolean, true if input is inside sublist
            choice = options.stream().map(e -> e.contains(input)).collect(Collectors.toList()).indexOf(true);

            if (choice != -1) {
                doLoop = false;
            } else {
                System.out.println("The chest doesn't understand that option");
            }
        }

        return choice;
    }

    @Override
    public String toString() {
        return (broken) ? "Broken Chest" : "Chest";
    }
}
