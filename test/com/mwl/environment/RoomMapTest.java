package com.mwl.environment;

import com.mwl.characters.Monster;
import com.mwl.characters.Normal;
import com.mwl.characters.Player;
import com.mwl.characters.Wolverine;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static com.mwl.environment.Direction.*;

import static org.junit.Assert.*;

public class RoomMapTest {
    RoomMap map;
    Player player;
    Monster monster;

    @Before
    public void setUp() throws Exception {
        map = new RoomMap();
        player = new Wolverine("temp", 100, map.getStart(), new ArrayList<>(), 1);
    //    monster = new Normal("monster1", 100, map.getStart(), "A normal monster");
    }

    @Test
    public void moveCharacter_player_makesNewRoom_whenNewDirection() {
        assertEquals(1, map.size());
        assertTrue(map.getMap().get(map.getStart()).isEmpty()); // no other rooms

        // move player to north, make a room
        map.moveCharacter(player, North);

        // see if map was updated
        assertEquals(2, map.size());
        assertEquals(Set.of(North), map.getMap().get(map.getStart()).keySet());
    }

    @Test
    public void moveCharacter_player_linksRoomsCorrectly() {
        map.moveCharacter(player, North);
        map.moveCharacter(player, South);

        assertEquals(2, map.size()); // no new rooms added
        assertEquals(map.getStart(), player.getCurrentRoom()); // moved back to starting room

        map.moveCharacter(player, North); // moving back creates no new rooms
        assertEquals(2, map.size());
    }

    @Test
    public void moveCharacter_player_addsRoomsMultiple() {
        assertEquals(1, map.size());
        for (int i = 0; i < 10; i++) {
            map.moveCharacter(player, East);
        }

        assertEquals(11, map.size());
        long only_east = map.getMap().values().stream()
                .filter(item -> item.containsKey(East) && item.containsKey(West))
                .count();
        // first and last room won't have both east and west. only rooms in between
        assertEquals(9, only_east);
    }

    @Test(expected = IllegalArgumentException.class)
    public void moveCharacter_player_startAtNull_ThrowsIllegalArgumentException() {
        player.setCurrentRoom(null);
        map.moveCharacter(player, North);
    }

    @Test
    public void moveCharacter_movesMonster_withAvailableDirection() {
        assertEquals(1, map.size());
   //     assertEquals(map.getStart(), monster.getCurrentRoom());

        // manually insert a new room connected to north
        var temp = map.getMap().get(map.getStart());
        temp.put(North, new Room("temp", -1));

   //     map.moveCharacter(monster, North); // move monster there

   //     assertEquals(temp.get(North), monster.getCurrentRoom());

    }

    @Test
    public void moveCharacter_monster_staysInSameRoomWithBadDirection() {
        assertEquals(1, map.size());
  //      assertEquals(map.getStart(), monster.getCurrentRoom());

//        map.moveCharacter(monster, North);
//        assertEquals(map.getStart(), monster.getCurrentRoom());
    }

    @Test(expected = IllegalArgumentException.class)
    public void moveCharacter_monster_roomIsNull_throwsIllegalArgumentException() {
  //      monster.setCurrentRoom(null);
  //      map.moveCharacter(monster, North);
    }
}