package com.palehorsestudios.ard.util;

import java.util.*;

public class InputValidation {
  public static String VALIDATE_CHARACTER_SELECTION(String userInput) throws InvalidInputException {
    StringBuilder character = new StringBuilder();
    userInput = userInput.toUpperCase().trim();
    List<String> A = new ArrayList<>(Arrays.asList("A", "WOLVERINE"));
    List<String> B = new ArrayList<>(Arrays.asList("B", "IRON MAN", "IRONMAN"));
    if (A.contains(userInput)) {
      character.append("A");
    } else if (B.contains(userInput)) {
      character.append("B");
    } else {
      throw new InvalidInputException("Invalid Input. Please choose one of the following: " + A + B);
    }

    return character.toString();
  }

  public static String VERB_SYNONYMS(String userInput) throws IllegalArgumentException {
    Map<String, List<String>> synonyms = new HashMap<>();
    synonyms = ConsoleManager.read_xml("synonyms/synonyms.xml", "command");
    StringBuilder synonym = new StringBuilder();
    for (Map.Entry<String, List<String>> verb : synonyms.entrySet()) {
      if(verb.getValue().contains(userInput)) {
        synonym.append(verb.getKey());
      }
    }
    return synonym.toString();
  }
}
