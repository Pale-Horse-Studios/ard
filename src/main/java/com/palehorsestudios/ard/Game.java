package com.palehorsestudios.ard;

import com.palehorsestudios.ard.characters.Monster;
import com.palehorsestudios.ard.characters.MonsterFactory;
import com.palehorsestudios.ard.characters.Player;
import com.palehorsestudios.ard.combat.combatEngine;
import com.palehorsestudios.ard.environment.Chest;
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
    // TODO: revise....at least access level
    void setPlayer(Player player) {
        this.player = player;
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

        if(command.length >= 2) {
            switch (command[0]) {
                case "move":
                    int size = getGameMap().size();
                    getGameMap().moveCharacter(getPlayer(), Direction.valueOf(command[1]));
                    responseBuilder.response("Moved " + Direction.valueOf(command[1]) + ".");
                    increaseScore(size);
                    break;
                case "look":
                    responseBuilder.response(Look(getPlayer(), command[1]));
                    break;
                case "drop":
                    responseBuilder.response(getPlayer().dropItem(command[1]));
                    break;
                case "help":
                    responseBuilder.response(ConsoleManager.gameExplanation());
                    break;
                case "unlock":
                    Chest chest = getPlayer().getCurrentRoom().getChest();
                    if(chest != null
                        && chest.isBroken()) {
                        responseBuilder.isQuestion(true);
                    }
                    responseBuilder.response(unlockChest(getPlayer()));
                    break;
                case "use":
                    responseBuilder.response(UsePower(getPlayer(), command[1]));
                    break;
                case "flight":
                    responseBuilder.response(Flight(getPlayer(), command[1]));
                    break;
                case "fight":
                    responseBuilder.response(Fight(getPlayer(), command[1]));
                    break;
                case "pickup":
                    responseBuilder.response(getPlayer().pickUpItem(command[1]));
                    break;
                default:
                    responseBuilder.response("Invalid command. Try again.");
                    break;
            }
        } else {
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
        if(!combatEngine.checkIfPlayerAlive(player)) {
            responseBuilder.gameOver(true);
        }
        return responseBuilder.build();
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
    String UsePower(Player player, String option) {
        StringBuilder vsb = new StringBuilder();
        vsb.append("use " + option);
        vsb.append("\n").append(player.useSpecialPower());
        return vsb.toString();
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
    String unlockChest(Player player) {
        return player.getCurrentRoom().unlockChest();
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
            writer = new PrintWriter(new FileWriter("resources/scores/final_scores.txt", true));
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
