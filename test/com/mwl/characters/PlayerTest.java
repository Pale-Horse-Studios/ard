package com.mwl.characters;

import com.mwl.environment.Item;
import com.mwl.environment.Room;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class PlayerTest {

    List<Item> playerInventory = new ArrayList<>();  //empty player inventory
    Room room1 = new Room("room", 1);  //empty room
    Player player = new Wolverine("Wolverine", 100, room1, playerInventory, 1);
    List<Item> testList1 = new ArrayList<>();  //empty list
    List<Item> testList2 = room1.getItems();   //list same as room inventory

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testPickUpItemPositive(){
        room1.addItem(Item.valueOf("Food"));  //add Food to room inventory
        player.pickUpItem(Item.valueOf("Food")); //player pickup Food

        testList1.add(Item.valueOf("Food"));
        assertEquals(player.getItemsInventory(), testList1); //assert player inventory ("Food") and testList1 ("Food")

        testList2.remove(Item.valueOf("Food"));
        assertEquals(room1.getItems(), testList2); //assert room inventory (no "Food") and testList2 (no "Food")
    }

    @Test
    public void testDropItem(){
        playerInventory.add(Item.valueOf("Milk")); //add Milk to player inventory

        player.dropItem(Item.valueOf("Milk")); //player drop off Milk
        assertEquals(player.getItemsInventory(), testList1); //assert player inventory (empty) and testList1 (empty)

        testList2.add(Item.valueOf("Milk"));
        assertEquals(room1.getItems(), testList2); //assert room inventory (with "Milk") and testList2 (with "Milk")
    }

}
