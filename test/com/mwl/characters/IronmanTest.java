package com.mwl.characters;

import com.mwl.environment.Item;
import com.mwl.environment.Room;
import org.junit.Test;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

public class IronmanTest {
    Room room1 = new Room("room", 1);
    List<Item> playerInventory = new ArrayList<>();
    Player player = new Ironman("Iron Man", 100, room1, playerInventory, 1);


    @Test
    public void testUseSpecialPowerPositive(){
        player.getItemsInventory().add(Item.valueOf("Power_stone"));
        player.useSpecialPower();
        assertEquals(1, player.getItemsInventory().size());

        Item[] items = {};
        items = player.getItemsInventory().toArray(items);
        assertTrue(Arrays.stream(Item.values()).anyMatch(items[0]::equals));
    }

    @Test
    public void testUseSpecialPowerNegative(){
        player.useSpecialPower();
        assertEquals(0, player.getItemsInventory().size());
    }
}
