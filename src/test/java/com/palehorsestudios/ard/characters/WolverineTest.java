package com.palehorsestudios.ard.characters;

import com.palehorsestudios.ard.environment.Item;
import com.palehorsestudios.ard.environment.Room;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class WolverineTest {
    Room room1 = new Room("room", 1);
    List<Item> playerInventory = new ArrayList<>();
    Player player = new Wolverine("Wolverine", 100, room1, playerInventory, 1);

    @Test
    public void testUseSpecialPowerPositive(){
        player.getItemsInventory().add(Item.valueOf("Power_stone"));
        player.useSpecialPower();
        assertEquals(150, player.getLife());
    }

    @Test
    public void testUseSpecialPowerNegative() {
        player.useSpecialPower();
        assertNotEquals(150, player.getLife());
        assertEquals(100, player.getLife());
    }
}
