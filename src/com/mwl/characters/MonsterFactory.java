package com.mwl.characters;

import com.mwl.environment.Item;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MonsterFactory {
    private static int startingLife = 50;

    private MonsterFactory() {

    }

    /**
     * Creates a normal monster, pulling name and description from a file.
     *
     * @return newly created normal monster
     */
    public static Monster createMonster() {
        Stream<String> content = null;
        try {
            content = Files.lines(Paths.get("resources/monsters/normal_monsters.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> monsters = content.collect(Collectors.toList());
        Random rand = new Random();
        int random = rand.nextInt(monsters.size());
        String[] str = monsters.get(random).split(",");

        Monster monster = new Normal(str[0], getMonsterStartingLife(), str[1].strip());

        return monster;
    }

    /**
     * Creates the boss monster if passed player has any of the infinity stones.
     *
     * @param player to check if player has an infinity stone inside their inventory
     * @return newly created boss monster
     */
    public static Monster createBossMonster(Player player) {
        Monster boss = null;
        if (player.getItemsInventory().contains(Item.valueOf("Soul_stone")) ||
                player.getItemsInventory().contains(Item.valueOf("Power_stone")) ||
                player.getItemsInventory().contains(Item.valueOf("Time_stone")) ||
                player.getItemsInventory().contains(Item.valueOf("Space_stone")) ||
                player.getItemsInventory().contains(Item.valueOf("Mind_stone")) ||
                player.getItemsInventory().contains(Item.valueOf("Reality_stone")))
            boss = new Boss("Bezos", 5, "The ultimate monster with great power.");

        return boss;
    }

    /**
     * Method to get the normal monsters typical starting life.
     *
     * @return
     */
    public static int getMonsterStartingLife() {
        return startingLife;
    }

    /**
     * Method to change the normal monster's typical starting life.
     *
     * @param startingLife
     */
    public static void setMonsterStartingLife(int startingLife) {
        MonsterFactory.startingLife = startingLife;
    }
}
