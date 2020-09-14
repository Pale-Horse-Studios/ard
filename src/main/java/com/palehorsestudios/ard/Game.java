package com.palehorsestudios.ard;

import com.palehorsestudios.ard.characters.Monster;
import com.palehorsestudios.ard.characters.MonsterFactory;
import com.palehorsestudios.ard.characters.Player;
import com.palehorsestudios.ard.combat.combatEngine;
import com.palehorsestudios.ard.environment.Direction;
import com.palehorsestudios.ard.environment.Item;
import com.palehorsestudios.ard.environment.RoomMap;
import com.palehorsestudios.ard.util.Codes;
import com.palehorsestudios.ard.util.ConsoleManager;
import com.palehorsestudios.ard.util.TextParser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Random;

import static com.palehorsestudios.ard.util.ExitGame.exit;

public class Game {
    private Player player;              // player reference
    private RoomMap gameMap;            // map of the rooms
    private Random random = new Random();
    private Monster boss;               // boss monster reference

    // default constructor
    public Game() {
        gameMap = new RoomMap();
    }

    public Player getPlayer() {
        return player;
    }

    public RoomMap getGameMap() {
        return gameMap;
    }

    public Monster getBoss() {
        return boss;
    }

    /**
     * Method to run the basic logic behind the game. Parse text, do command, return boolean if game is still going.
     *
     * @return
     */
    Response play(String cmd) {
        String[] command = TextParser.parser(cmd);
        Response.Builder responseBuilder = new Response.Builder();
        switch (command[0]) {
//      case "move":
//        int size = game.getGameMap().size();
//        game.getGameMap().moveCharacter(game.getPlayer(), Direction.valueOf(command[1]));
//        game.increaseScore(size);
//        break;
            case "look":
                responseBuilder.response(Look(getPlayer(), command[1]));
                break;
            case "flight":
                responseBuilder.response(Flight(getPlayer(), command[1]));
                break;
            case "fight":
                responseBuilder.response(Fight(getPlayer(), command[1]));
                break;
//      case "pickup":
//        game.getPlayer().pickUpItem(Item.valueOf(command[1]));
//        break;
//      case "drop":
//        game.getPlayer().dropItem(Item.valueOf(command[1]));
//        break;
//      case "help":
//        ConsoleManager.gameExplanation();
//        break;
//      case "unlock":
//        game.unlockChest(game.getPlayer());
//        break;
//      case "use":
//        game.UsePower(game.getPlayer(), command[1]);
            default:
                responseBuilder.response("Invalid command. Try again.");
        }
        if (boss == null) {
            boss = MonsterFactory.createBossMonster(player);
            player.getCurrentRoom().addMonster(boss);
        }
        if (boss != null && boss.getLife() <= 0) {
            responseBuilder.gameOverResult(Codes.Player.withColor(player.getName()) + " killed "
                + Codes.Monster.withColor(boss.getName()) + "! You win!!!!");
            keepScores(player);
            responseBuilder.gameOver(true);
        }
        if(combatEngine.checkIfPlayerAlive(player)) {
            responseBuilder.gameOver(true);
        }
        return responseBuilder.build();
    }

    /**
     * Method to start a new game. Prints out a welcome message and game banner.
     */
    public void newGame() {
        // new game logic
        ConsoleManager.gameIntro();
        player = ConsoleManager.choosePlayer(gameMap);

        // quit message
//        System.out.println("Thanks for playing! Come play again");

    }

    /*
    Stubbed out method to prepare for flight action
     */
    String Flight(Player player, String option) {
        return "Flying " + option;
        // run method to do the action
    }

    /**
     * Method to instigate player fighting. Calls player's attack method
     */
    String Fight(Player player, String option) {
        StringBuilder sb = new StringBuilder();
        sb.append("fighting ").append(option).append("\n");
        sb.append(player.attack());
        return sb.toString();
    }

    /**
     * Method to instigate player using their special power.
     */
    void UsePower(Player player, String option) {
        System.out.println("use " + option);
        player.useSpecialPower();
    }

    /**
     * Method to look at different objects. Either "Around" to give details about the room. "Me" to give details about the
     * player.
     */
    String Look(Player player, String option) {
        switch (option) {
            case "Around":
                return player.getCurrentRoom().overview();
            case "Me":
                return player.printStats();
            default:
                return itemRequestDesc(option);
        }
    }

    /**
     * Method to invoke unlock chest method
     */
    void unlockChest(Player player) {
        player.getCurrentRoom().unlockChest();
    }

    /**
     * Method to call increment score for the player when the gamemap has increased in size.
     */
    void increaseScore(int previousSize) {
        int newSize = gameMap.size();
        if (newSize > previousSize) {
            player.incrementScore();
        }
    }

    public String itemRequestDesc(String item){
        StringBuilder sb = new StringBuilder();
        if(player.playerAndRoomItems().contains(Item.valueOf(item))){
            for (Item itemx : player.playerAndRoomItems()) {
                if (itemx.name().equals(item)){
                    sb.append("\n").append(itemx.getDescription());
                    break;
                }
            }
        }else {
            sb.append("Item not present");
        }
        return sb.toString();
    }


    public static void keepScores(Player player) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter("src/main/resources/scores/final_scores.txt", true));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Please enter your name here to record your " + Codes.Score.withColor("score") + " for this game: ");
        String name = ConsoleManager.scanner().nextLine();

        LocalDateTime time = LocalDateTime.now();
        writer.append("<Final score for this game @" + time + ">" + "\n");
        writer.append("[" + name + "] (" + player.getName() + "): " + player.getScore() + " points \n");
        writer.println();

        writer.close();
    }
}

