package com.mwl.characters;

import com.mwl.environment.Item;
import com.mwl.environment.Room;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;


public class MonsterFactoryTest {
    Room room1 = new Room("room", 1);
    List<Item> playerInventory = new ArrayList<>();
    Player player = new Wolverine("Wolverine",100, room1, playerInventory, 1);

    @Test
    public void testCreateBossMonsterPositive(){
        Monster boss = new Boss("Bezos", 5, "The ultimate monster with great power.");
        player.getItemsInventory().add(Item.valueOf("Power_stone"));
        assertEquals(boss.toString(), MonsterFactory.createBossMonster(player).toString());
    }

    @Test
    public void testCreateBossMonsterNegative(){
        assertEquals(null, MonsterFactory.createBossMonster(player));
    }
}
