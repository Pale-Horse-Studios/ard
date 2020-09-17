package com.palehorsestudios.ard.util;

import com.palehorsestudios.ard.characters.Player;
import com.palehorsestudios.ard.characters.PlayerFactory;
import com.palehorsestudios.ard.environment.RoomMap;
import com.palehorsestudios.ard.util.commands.Commands;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.palehorsestudios.ard.util.ExitGame.exit;

public class ConsoleManager {
    private static final Scanner scanner = new Scanner(System.in);
    private static MenuTrieNode menu = read_xml();

    public ConsoleManager() {
    }

    /**
     * Method to read a random ASCII art game banner, and return it as a string. If error out reading file, returns
     * "A. R. D." as the banner.
     *
     * @return a ASCII art banner as a single string
     */
    private static String gameTitle() {
        String result;
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("src/main/resources/title_art/banners.xml");
            NodeList banners = document.getElementsByTagName("banner");
            result = banners.item(ThreadLocalRandom.current().nextInt(banners.getLength())).getTextContent();
        } catch (IOException | ParserConfigurationException | SAXException e) {
            result = "A. R. D.\n\n";
        }

        return result;
    }

    public static String banner() {
        StringBuilder banner = new StringBuilder();
        banner.append(getRandomColor().toColor(gameTitle()));
        return banner.toString();
    }

    /**
     * Method to print out welcome messages for a new game.
     * Game title (banner)
     * Game description
     */
    public static String gameIntro() {
        StringBuilder intro = new StringBuilder();
        intro.append("Welcome to " + Colors.CYAN.negative("ARD") + ", the game where you get "
                + Colors.CYAN.toColor("Another Random Destiny") + " every time you play!");
        intro.append("\nTo learn about the game, type \"help me\".");

        intro.append("\n\n").append("Please just type in the letter 'A' or 'B' to choose the type of ")
            .append("\n").append(Codes.Player.withColor("player")).append(" you want to play with.")
            .append("\n").append("A: ").append(Codes.Player.withColor("Wolverine")).append(" has special ability of health boost.")
            .append("\n").append("B: ").append(Codes.Player.withColor("Iron Man")).append(" has special ability to randomly generate one item that's already in inventory.");

        return intro.toString();
    }

    /**
     * helper method to get a random color from Colors enum
     *
     * @return a random Colors enum
     */
    private static Colors getRandomColor() {
        Colors[] colors = Colors.values();
        return colors[ThreadLocalRandom.current().nextInt(colors.length)];
    }

    /**
     * Helper method to get input about traversing the help menu. Users can also choose to go back a level or to quit
     * the help menu.
     *
     * @param options options to choose from for navigating
     * @return which choice from options. Returns -1 if going back a level or -2 if quitting help menu
     */
    private static int getInput(List<List<String>> options) {
        options.add(List.of("b", "back"));
        options.add(List.of("q", "quit"));
        boolean doLoop = true;
        int choice = -1;
        while (doLoop) {
            options.forEach(System.out::println); // print out the options
            String input = scanner.nextLine().strip(); // get the choice from console

            // map each item in options to be boolean, true if input is inside sublist
            choice = options.stream().map(e -> e.contains(input)).collect(Collectors.toList()).indexOf(true);

            if (choice != -1) {
                doLoop = false;
            } else {
                System.out.println("I didn't understand that option, please try again.");
            }
        }

        return (choice == options.size() - 1 ? -1 : choice == options.size() - 2 ? -2 : choice); // -> -1 or -2 or some number in list
    }

    /**
     * Method to navigate the help menu. Prints out the current level title, description, and asks for input to go to
     * other levels for game information.
     */
    public static String gameExplanation() {
        StringBuilder vsb = new StringBuilder();
        boolean navigateMenu = true;
        MenuTrieNode curr = menu;
        while (navigateMenu) {
            vsb.append("<").append(curr.getTitle()).append(">");
            vsb.append("\n").append(curr.getDescription());
            MenuTrieNode finalCurr = curr;
            int choice = getInput(IntStream.range(0, curr.getChildren().size())
                    .mapToObj(e -> List.of("" + e, finalCurr.getChild(e).getTitle()))
                    .collect(Collectors.toList())); // -> {{"0", "Story details"}, {"1", "Game controls"}} etc.

            if (choice == -1) {
                navigateMenu = false;
            } else if (choice == -2) {
                curr = curr.getParent();
            } else {
                curr = curr.getChild(choice);
            }
        }
        return vsb.toString();
    }

    /**
     * Method to choose a player class from given options.
     *
     * @param map Game map to get starting point for player.
     * @return newly created player
     */
    public static Player choosePlayer(RoomMap map) {
//        String[] instructions = {
//                "Please just type in the letter 'A' or 'B' to choose the type of "
//                        + Codes.Player.withColor("player") + " you want to play with.",
//                "A: [" + Codes.Player.withColor("Wolverine") + "] has special ability of health boost;\n" +
//                        "B: [" + Codes.Player.withColor("Iron Man") + "] has special ability to randomly " +
//                        "generate one item that's already in inventory.",
//                "Wrong input!\n" + "Enter A or B: ",
//        };
//
//        System.out.println(instructions[0]);
//        System.out.println(instructions[1]);
//        String playerChoice = scanner.nextLine();
//        exit(playerChoice);
//        while (!playerChoice.toUpperCase().strip().equals(Character.toString('A')) &&
//                !playerChoice.toUpperCase().strip().equals(Character.toString('B'))) {
//            System.out.println(instructions[2]);
//            System.out.println(instructions[0]);
//            System.out.println(instructions[1]);
//            playerChoice = scanner.nextLine();
//            exit(playerChoice);
//        }
//        String playName = (playerChoice.toUpperCase().strip().equals(Character.toString('A'))) ? "Wolverine" : "Iron Man";
//        System.out.println("Player type: [" + Codes.Player.withColor(playName) + "] has been chosen.");

//        return PlayerFactory.createPlayer(map.getStart(), new ArrayList<>(), playerChoice);
        return PlayerFactory.createPlayer(map.getStart(), new ArrayList<>(), "A");
    }

    /**
     * method made package level access only on purpose
     * <p>
     * Helper method to load in trieNodes from a given Document Node. Converting to TrieNodes for ease of traversing
     * while in game, and less overhead.
     *
     * @param current
     * @return
     */
    static MenuTrieNode recursiveHelper(Node current) {
        List<MenuTrieNode> result = new ArrayList<>();
        NodeList children = current.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element temp = (Element) children.item(i);
                if (temp.getParentNode().equals(current)) {
                    result.add(recursiveHelper(children.item(i)));
                }
            }
        }

        Element temp = (Element) current;
        MenuTrieNode returning = new MenuTrieNode(temp.getAttribute("title"), temp.getAttribute("description"));
        result.forEach(menuTrieNode -> {
            returning.addChild(menuTrieNode);
            menuTrieNode.setParent(returning);
        });

        return returning;
    }

    /**
     * method made package level access only on purpose
     * <p>
     * Read in an xml file containing all of the help menu titles and descriptions, as well as the organization.
     * Converts the xml file into a MenuTrieNode structure (e.g. a trie) for use as a help menu.
     *
     * @return root of MenuTrie
     */
    static MenuTrieNode read_xml() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        NodeList menuNodeList = null;

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("src/main/resources/menu/help_menu.xml");
            menuNodeList = doc.getElementsByTagName("menu");

            MenuTrieNode menu = recursiveHelper(menuNodeList.item(0));
            menu.setParent(menu);
            return menu;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        MenuTrieNode failure = new MenuTrieNode("Help Menu", "There was an error reading the help menu," +
                " please restart the game to try again.");
        failure.setParent(failure);

        return failure;
    }

    static Map<String, List<String>> read_xml(String fileName, String tagName) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Map<String, List<String>> result = new HashMap<>();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("src/main/resources/".concat(fileName));
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName(tagName);

            for (int i = 0; i < list.getLength(); i++) {
                List<String > synonyms = new ArrayList<>();

                Node node = list.item(i);
                Element parent = (Element) node;

                NodeList children = node.getChildNodes();
                synonyms.add(parent.getAttribute(tagName));

                for (int j = 0; j < children.getLength(); j++) {
                    Node child = children.item(j);
                    synonyms.add(child.getTextContent());
                }
                result.put(parent.getAttribute(tagName), synonyms);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * scanInput gets user input and validates input
     *
     * @param commands
     * @param str
     * @return Array of valid words
     */
    public static String[] scanInput(Map<String, Commands> commands, String str) {
        String playerInput = str.strip().toLowerCase();
        String[] words;
        words = playerInput.split("\\W+");
        words[0] = InputValidation.VERB_SYNONYMS(words[0]);

            if (words.length == 2) {
                if (commands.containsKey(words[0])) {
                    try {
                        words[1] = words[1].substring(0, 1).toUpperCase() + words[1].substring(1);
                        commands.get(words[0]).do_command(words[1]);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Not a valid action. Try again!");
                    }
                }
            }
        return words;
    }

    /**
     * Scanner class enable used of the same scanner outside class.
     *
     * @return scanner
     */
    public static Scanner scanner() {
        return scanner;
    }
}
