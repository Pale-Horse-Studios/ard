package com.mwl.characters;
import com.mwl.environment.Item;
import com.mwl.environment.Room;
import com.mwl.util.Codes;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public abstract class Player {
    private String name;
    private int life;
    private Room currentRoom;
    private List<Item> itemsInventory;
    private int level;
    private int score = 0;

    public Player() {
    }

    public Player(String name, int life, Room currentRoom, List<Item> itemsInventory, int level) {
        this.name = name;
        this.life = life;
        this.currentRoom = currentRoom;
        this.itemsInventory = itemsInventory;
        this.level = level;
        this.score = getScore();
    }


    /**
     * If the item is in current room, add the item picked up by the user into the item inventory
     * and remove the item from the room item list
     * if input is not in Item Enum, throw Exception
     */
    public void pickUpItem(Item item) {
        if (currentRoom.getItems().contains(item)) {
            itemsInventory.add(item);
            currentRoom.grabItem(item);
        } else {
            System.out.println("Can't pick up! This item is not in the current room!");
        }

    }

    /**
     * If the item is in player inventory, remove the item dropped by the user from the item inventory
     * and add the item into the room item list
     * if input is not in Item Enum, throw Exception
     */
    public void dropItem(Item item) {
        if (itemsInventory.contains(item)) {
            itemsInventory.remove(item);
            currentRoom.addItem(item);
        } else {
            System.out.println("Can't drop this item! It's not in player's item inventory!");
        }
    }

    /**
     * Abstract method to attack a monster
     */
    public abstract void attack();

    /**
     * Abstract method to use player's special power.
     */
    public abstract void useSpecialPower();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public List<Item> getItemsInventory() {
        return itemsInventory;
    }

    public void setItemsInventory(List<Item> itemsInventory) {
        this.itemsInventory = itemsInventory;
    }

    public void printStats() {
        System.out.println(Codes.Player.getCode() + Codes.Player.withColor(getName()));
        System.out.println(Codes.Life.getCode() + Codes.Life.withColor(getLife()));
        System.out.println(Codes.Room.getCode() + Codes.Room.withColor("Room " + getCurrentRoom().getId()));
        System.out.println(Codes.Item.getCode() + getItemsInventory().stream()
                .map(e -> Codes.Item.withColor(e.toString())).collect(Collectors.joining(", ")));
        System.out.println(Codes.Score.getCode() + Codes.Score.withColor(" Score " + getScore()));
        System.out.println(Codes.Level.getCode() + Codes.Level.withColor(" Level " + getLevel()));
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void incrementScore() {
        setScore(getScore() + 10);
    }

    public Set<Item> playerAndRoomItems() {
        Set<Item> newList = Stream.of(getItemsInventory(), getCurrentRoom().getItems())
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        return newList;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", life=" + life +
                ", currentRoom=" + currentRoom +
                ", itemsInventory=" + itemsInventory +
                ", level=" + level +
                ", score=" + score +
                '}';
    }
}
