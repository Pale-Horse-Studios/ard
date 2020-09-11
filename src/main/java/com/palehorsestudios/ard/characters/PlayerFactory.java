package com.palehorsestudios.ard.characters;

import com.palehorsestudios.ard.environment.Item;
import com.palehorsestudios.ard.environment.Room;

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
        }
        return player;
    }
}
