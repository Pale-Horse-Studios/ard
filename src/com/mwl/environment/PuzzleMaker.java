package com.mwl.environment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class PuzzleMaker {

    private NodeList puzzles;

    /**
     * Constructor. Reads in a xml file from a specific source. Ready to be used to construct puzzles
     */
    public PuzzleMaker() {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("resources/puzzles/many_questions.xml");
            puzzles = document.getElementsByTagName("puzzle");
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Picks a random puzzle element and pulls its child tags. Returns a new Puzzle object using those tags
     *
     * @return
     */
    public Puzzle getRandomPuzzle() {
        Element node = (Element) puzzles.item(ThreadLocalRandom.current().nextInt(puzzles.getLength()));
        String question = getContent(node, "question");
        String difficulty = getContent(node, "difficulty");
        String answer = getContent(node, "correct_answer");
        NodeList temp = node.getElementsByTagName("element");
        Set<String> wrong = new HashSet<>();
        for (int i = 0; i < temp.getLength(); i++) {
            wrong.add(temp.item(i).getTextContent());
        }

        return new Puzzle(question, difficulty, answer, wrong);
    }

    /**
     * Helper method to get text content from a tag.
     */
    private String getContent(Element e, String tag) {
        return e.getElementsByTagName(tag).item(0).getTextContent();
    }

}
