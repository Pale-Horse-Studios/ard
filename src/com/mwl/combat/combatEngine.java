package com.mwl.combat;

import com.mwl.ard.Game;
import com.mwl.characters.Monster;
import com.mwl.characters.Player;
import com.mwl.environment.Room;
import com.mwl.util.Codes;

import java.util.concurrent.ThreadLocalRandom;

import static com.mwl.combat.WinOrLose.LevelUp;
import static com.mwl.util.ExitGame.exit;

public class combatEngine {

    /**
     * static method a player can call to attack monsters
     *
     * @param player current player
     */
    public static void fightRoomMonster(Player player) {
        String[] fights = {
                " fiercely hit ",
                " successfully evaded and furiously punched ",
                " heavily overthrew ",
                " swiftly elbowed ",
                " unexpected kicked ",
        };
        int rand = ThreadLocalRandom.current().nextInt(fights.length);

        if (checkForMonsterInRoom(player.getCurrentRoom())) {
            Monster monster = player.getCurrentRoom().getMonsters().get(0);
            int lifeValue = monster.getLife();
            int damage = randomDamage();
            lifeValue -= damage;
            monster.setLife(lifeValue);
            if (!checkIfMonsterAlive(player.getCurrentRoom())) {
                removeDefeatedMonsterFromRoom(player.getCurrentRoom());
                LevelUp(player);
                player.incrementScore();
                System.out.println(Codes.Player.withColor(player.getName()) + " killed " + Codes.Monster.withColor(monster.getName()));
            } else {
                System.out.println(Codes.Player.withColor(player.getName()) + fights[rand]
                        + Codes.Monster.withColor(monster.getName()) + " and "
                        + Codes.Monster.withColor(monster.getName()) + " lost life value of: "
                        + Codes.Monster.getColor().negative(damage));
                System.out.println(Codes.Monster.withColor(monster.getName()) + " current life value is: "
                        + Codes.Life.withColor(lifeValue));
            }
        }
    }

    /**
     * static method that runs for the monster to fight back when a player attacks
     *
     * @param monster the monster in the current room.
     * @param player  the player currently in the room with the monster.
     */
    public static void MonsterFightsPlayer(Monster monster, Player player) {
        String[] attacks = {
                " violently bit ",
                " quietly stalked and suddenly attacked ",
                " smartly dodged and viciously clawed ",
                " aggressively knocked down ",
                " ruthlessly hit "
        };
        int rand = ThreadLocalRandom.current().nextInt(attacks.length);

        if (checkIfMonsterAlive(player.getCurrentRoom())) {
            int lifeValue = player.getLife();
            int damage = randomDamage();
            lifeValue -= damage;
            player.setLife(lifeValue);
            if (!checkIfPlayerAlive(player)) {
                ifPlayerDeath(monster, player);
            } else {
                System.out.println(Codes.Monster.withColor(monster.getName()) + attacks[rand]
                        + Codes.Player.withColor(player.getName()) + " and "
                        + Codes.Player.withColor(player.getName()) + " lost life value of: "
                        + Codes.Player.getColor().negative(damage));
                System.out.println(Codes.Player.withColor(player.getName()) + " current life value is: "
                        + Codes.Life.withColor(lifeValue));
            }
        }

    }

    /**
     * Helper method for fightRoomMonster to check if a monster is in the room
     *
     * @param currentRoom Room that player is currently in
     * @return return true if a monster is present in the current room, false otherwise
     */
    public static boolean checkForMonsterInRoom(Room currentRoom) {
        boolean monsterPresent = true;
        if (currentRoom.getMonsters().size() == 0) {
            System.out.println("Just kidding! No monster in this room");
            monsterPresent = false;
        }
        return monsterPresent;
    }

    /**
     * Removes a monster from current room.
     *
     * @param currentRoom Room where player is currently at.
     */
    private static void removeDefeatedMonsterFromRoom(Room currentRoom) {
        currentRoom.defeatMonster(currentRoom.getMonsters().get(0));
    }

    /**
     * return true if current room's, monster's life is more than 0, false otherwise
     * package private for test purposes
     *
     * @param currentRoom Room where player is currently at
     * @return true or false depending if a monster is in the room
     */
    static boolean checkIfMonsterAlive(Room currentRoom) {
        boolean alive = false;
        if (currentRoom.getMonsters().get(0).getLife() > 0) {
            alive = true;
        }
        return alive;
    }

    /**
     * returns true or false depending if current player's life is more than 0
     * package private for test purposes
     *
     * @param player current game player
     * @return true or false based on player's life value
     */
    static boolean checkIfPlayerAlive(Player player) {
        boolean alive = false;
        if (player.getLife() > 0) {
            alive = true;
        }
        return alive;
    }

    /**
     * end the game when monster kills current player
     *
     * @param monster current room's monster
     */
    private static void ifPlayerDeath(Monster monster, Player player) {
        System.out.println("Sorry " + Codes.Monster.withColor(monster.getName()) + " killed "
                + Codes.Player.withColor("you."));
        Game.keepScores(player);
        exit("exit");
    }

    /**
     * generates random number to simulate damage
     *
     * @return random int
     */
    private static int randomDamage() {
        return ThreadLocalRandom.current().nextInt(0, 30);
    }
}