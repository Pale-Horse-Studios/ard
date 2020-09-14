package com.palehorsestudios.ard.combat;

import com.palehorsestudios.ard.Game;
import com.palehorsestudios.ard.characters.Monster;
import com.palehorsestudios.ard.characters.Player;
import com.palehorsestudios.ard.environment.Room;
import com.palehorsestudios.ard.util.Codes;

import java.util.concurrent.ThreadLocalRandom;

import static com.palehorsestudios.ard.combat.WinOrLose.LevelUp;

public class combatEngine {

  /**
   * static method a player can call to attack monsters
   *
   * @param player current player
   */
  public static String fightRoomMonster(Player player) {
    StringBuilder sb = new StringBuilder();
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
        sb.append(Codes.Player.withColor(player.getName()))
            .append(" killed ")
            .append(Codes.Monster.withColor(monster.getName()));
      } else {
        sb.append(Codes.Player.withColor(player.getName()))
            .append(fights[rand])
            .append(Codes.Monster.withColor(monster.getName()))
            .append(" and ")
            .append(Codes.Monster.withColor(monster.getName()))
            .append(" lost life value of: ")
            .append(Codes.Monster.getColor().negative(damage));
        sb.append(Codes.Monster.withColor(monster.getName()))
            .append(" current life value is: ")
            .append(Codes.Life.withColor(lifeValue));
      }
    }
    return sb.toString();
  }

  /**
   * static method that runs for the monster to fight back when a player attacks
   *
   * @param monster the monster in the current room.
   * @param player the player currently in the room with the monster.
   */
  public static String MonsterFightsPlayer(Monster monster, Player player) {
    StringBuilder sb = new StringBuilder();
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
        sb.append(ifPlayerDeath(monster, player));
      } else {
        sb.append(Codes.Monster.withColor(monster.getName()))
            .append(attacks[rand])
            .append(Codes.Player.withColor(player.getName()))
            .append(" and ")
            .append(Codes.Player.withColor(player.getName()))
            .append(" lost life value of: ")
            .append(Codes.Player.getColor().negative(damage));
        sb.append(Codes.Player.withColor(player.getName()))
            .append(" current life value is: ")
            .append(Codes.Life.withColor(lifeValue));
      }
    }
    return sb.toString();
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
    public static boolean checkIfPlayerAlive(Player player) {
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
    private static String ifPlayerDeath(Monster monster, Player player) {
        Game.keepScores(player);
        return "Sorry " + Codes.Monster.withColor(monster.getName()) + " killed "
                + Codes.Player.withColor("you.");
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