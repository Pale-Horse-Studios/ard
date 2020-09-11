package com.palehorsestudios.ard.characters;

import com.palehorsestudios.ard.environment.Item;
import com.palehorsestudios.ard.environment.Room;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class PlayerFactoryTest {
    Room room1 = new Room("room",1);
    List<Item> playerInventory = new ArrayList<>();
    Player player1 = new Wolverine("Wolverine", 100, room1, playerInventory, 1);
    Player player2 = new Ironman("Iron Man", 100, room1, playerInventory, 1);


    @Test
    public void testCreatePlayerPositive(){
      assertEquals(player1.toString(), PlayerFactory.createPlayer(room1, playerInventory, "A").toString());
      assertEquals(player2.toString(), PlayerFactory.createPlayer(room1, playerInventory, "B").toString());
    }

    @Test
    public void testCreatePlayerNegative(){
        assertEquals(null, PlayerFactory.createPlayer(room1, playerInventory, "falskfalkfj"));
        assertEquals(null, PlayerFactory.createPlayer(room1, playerInventory, "C"));
        assertEquals(null, PlayerFactory.createPlayer(room1, playerInventory, ""));

    }
}
