package com.palehorsestudios.ard.characters;

import com.palehorsestudios.ard.environment.Item;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
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
        List<String> monsters = new LinkedList<>();
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ClassPathResource("monsters/normal_monsters.txt").getInputStream()))) {
            String line = bufferedReader.readLine();
            while(line != null) {
                monsters.add(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Random rand = new Random();
        int random = rand.nextInt(monsters.size());
        String[] str = monsters.get(random).split(",");

        return new Normal(str[0], getMonsterStartingLife(), str[1].strip());
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
