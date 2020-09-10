package com.mwl.characters;

import com.mwl.environment.Item;
import com.mwl.environment.Room;

import java.util.List;

public class PlayerFactory {

    private PlayerFactory() {

    }

    public static Player createPlayer(Room currentRoom, List<Item> itemInventory, String playerOption) {
        Player player = null;
        switch (playerOption.toUpperCase().strip()) {
            case "A":
                player = new Wolverine("Wolverine", 100, currentRoom, itemInventory, 1);
                break;
            case "B":
                player = new Ironman("Iron Man", 100, currentRoom, itemInventory, 1);
                break;
        }
        return player;
    }
}
