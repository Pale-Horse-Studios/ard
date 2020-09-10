package com.mwl.environment;

import com.mwl.characters.Monster;
import com.mwl.characters.MonsterFactory;
import com.mwl.util.Codes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

public class Room {
    private String description; // description of the room
    private List<Item> items; // list of items in room
    private List<Monster> monsters; // list of monsters in room
    private final int id; // room id (for ensuring hashcode is different)
    private Random random = new Random(); // Generate random numbers
    private Chest chest; // a chest of reward items


    /**
     * Constructor
     *
     * @param description
     * @param id
     */
    public Room(String description, int id) {
        this.description = description;
        this.id = id;
        items = new ArrayList<>();
        monsters = new ArrayList<>();
        generateRandomRoomItems();
        generateRandomNormalMonsters();
    }

    /**
     * Return room description
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Return all items in room
     *
     * @return items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Return all monsters in room
     *
     * @return
     */
    public List<Monster> getMonsters() {
        return monsters;
    }

    /**
     * Returns room id
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Adds item to room's item list
     *
     * @param item
     */
    public void addItem(Item item) {
        if (item != null) {
            items.add(item);
        }
    }

    /**
     * generates between 0 and 3 items in rooms randomly
     */
    private void generateRandomRoomItems() {
        //Returns a random number.
        //between 0 (inclusive) and 3 (exclusive).
        if (this.id <= 5) {
            int quantity = random.nextInt(3);
            for (int i = 0; i < quantity; i++) {
                addItem(Item.values()[random.nextInt(6)]);
            }
        } else if (this.id > 5) {
            int quantity = random.nextInt(6);
            for (int i = 0; i < quantity; i++) {
                addItem(Item.values()[random.nextInt(12)]);
            }
        }
    }

    /**
     * Adds monster to room's monster list
     *
     * @param monster
     */
    public void addMonster(Monster monster) {
        if (monster != null) {
            monsters.add(monster);
        }
    }

    //randomly generate normal monsters with a probability of 80%
    public void generateRandomNormalMonsters() {
        int number = random.nextInt(100);
        if (number < 80) {
            addMonster(MonsterFactory.createMonster());
        }
    }

    /**
     * Adds a list of items to room's item list.
     *
     * @param items
     */
    public void addAllItems(List<Item> items) {
        if (items != null) {
            this.items.addAll(items);
        }
    }

    /**
     * Adds a list of monsters to room's monster list
     *
     * @param monsters
     */
    public void addAllMonsters(List<Monster> monsters) {
        if (monsters != null) {
            this.monsters.addAll(monsters);
        }
    }

    /**
     * Grab an item from room. Removes item from room's item list, if present. Returns a boolean if successfully
     * removed the item.
     *
     * @param item
     * @return
     */
    public boolean grabItem(Item item) {
        return items.remove(item);
    }

    /**
     * Removes monster from room's monster list, if present. Returns a boolean if successfully
     * removed the monster.
     *
     * @param monster
     * @return
     */
    public boolean defeatMonster(Monster monster) {
        return monsters.remove(monster);
    }


    /**
     * Brief overview of what is in a room
     */
    public void overview() {
        System.out.println(Codes.Room.getCode() + "You are in " + Codes.Room.withColor("Room " + getId()));
        String temp = Codes.Left.getCode() + Codes.Left.withColor(" " + getDescription() + " ") + Codes.Right.getCode();
        System.out.println(Codes.Left.getCode() + Codes.Left.withColor(" " + getDescription() + " ") + Codes.Right.getCode());
        System.out.println(Codes.Item.getCode() + itemsPresent());
        if (chest != null) {
            System.out.println(Codes.Chest.getCode() + Codes.Chest.withColor(" " + chest.toString()));
        }
        System.out.println(Codes.Monster.getCode() + monstersPresent());
    }

    /**
     * Check for items present in a room
     *
     * @return prints list of items present or no items message
     */
    private String itemsPresent() {
        if (getItems().size() > 0) {
            return getItems().size() + " item(s): " + getItems().stream().map(e -> Codes.Item.withColor(e.toString())).collect(Collectors.joining(", "));
        } else {
            return "No items present in this room.";
        }
    }

    /**
     * Check for monsters present in a room
     *
     * @return Prints list of monsters or no monsters message
     */
    private String monstersPresent() {
        if (getMonsters().size() > 0) {
            return getMonsters().size() + " monster:" + getMonsters().toString();
        } else {
            return "No monsters present in this room.";
        }
    }

    /**
     * Method to set the chest for the given room.
     *
     * @param chest
     */
    public void setChest(Chest chest) {
        this.chest = chest;
    }

    /**
     * Runs the chest's question (if available) and get the reward from the chest. Adds rewarded items to the room's
     * inventory.
     */
    public void unlockChest() {
        if (chest != null) {
            List<Item> reward = chest.askQuestion();
            if (reward.size() > 0) {
                System.out.println("The " + Codes.Chest.withColor("chest") + " empties its contents onto the floor.");
            }
            this.addAllItems(reward);
        } else {
            System.out.println("No " + Codes.Chest.withColor("chest") + " in this room.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return getId() == room.getId() &&
                getDescription().equals(room.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription(), getId());
    }
}
