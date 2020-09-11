package com.palehorsestudios.ard.combat;

import com.palehorsestudios.ard.characters.Monster;
import com.palehorsestudios.ard.characters.Player;
import com.palehorsestudios.ard.characters.Wolverine;
import com.palehorsestudios.ard.environment.Room;
import org.junit.Test;

import static com.palehorsestudios.ard.characters.MonsterFactory.createMonster;
import static com.palehorsestudios.ard.combat.combatEngine.*;
import static org.junit.Assert.*;

public class combatEngineTest {
    Room currentRoom = new Room("desc", 3);
    Player player = new Wolverine("Wolverine", 100, currentRoom, currentRoom.getItems(), 1);
    Monster monster = createMonster();

    /**
     * Passed if monster not in room
     */
    @Test
    public void checkForMonsterInRoomNegativeTest() {
        boolean actual = false;
        currentRoom.addMonster(monster);
        currentRoom.getMonsters().clear();
        boolean expected = checkForMonsterInRoom(currentRoom);
        assertEquals(expected, actual);
    }

    /**
     * Passed if Monster in room
     */
    @Test
    public void checkForMonsterInRoomPositiveTest() {
        boolean actual = true;
        currentRoom.getMonsters().clear();
        currentRoom.addMonster(monster);
        boolean expected = checkForMonsterInRoom(currentRoom);
        assertEquals(expected, actual);
    }

    /**
     * Passed if player has one life point
     */
    @Test
    public void checkIfPlayerAlivePositiveOneBoundaryTest() {
        player.setLife(1);
        assertTrue(checkIfPlayerAlive(player));
    }

    /**
     * Passed if player has 0 life point, meaning is death.
     */
    @Test
    public void checkIfPlayerAliveNegativeBoundaryTestWithZeroLife() {
        player.setLife(0);
        assertFalse(checkIfPlayerAlive(player));
    }

    /**
     * Passed if player has -1 life point, meaning is very death.
     */
    @Test
    public void checkIfPlayerAliveNegativeOneBoundaryTest() {
        player.setLife(-1);
        assertFalse(checkIfPlayerAlive(player));
    }

    /**
     * Passed if player has one life point
     */
    @Test
    public void checkIfMonsterAlivePositiveOneBoundaryTest() {
        currentRoom.addMonster(monster);
        currentRoom.getMonsters().get(0).setLife(1);
        assertTrue(checkIfMonsterAlive(currentRoom));
    }

    /**
     * Passed if player has 0 life point, meaning is death.
     */
    @Test
    public void checkIfMonsterAliveNegativeBoundaryTestWithZeroLife() {
        currentRoom.addMonster(monster);
        currentRoom.getMonsters().get(0).setLife(0);
        assertFalse(checkIfMonsterAlive(currentRoom));
    }

    /**
     * Passed if player has -1 life point, meaning is very death.
     */
    @Test
    public void checkIfMonsterAliveNegativeOneBoundaryTest() {
        currentRoom.addMonster(monster);
        currentRoom.getMonsters().get(0).setLife(-1);
        assertFalse(checkIfMonsterAlive(currentRoom));
    }
}