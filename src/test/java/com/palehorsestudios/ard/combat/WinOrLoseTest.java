package com.palehorsestudios.ard.combat;

import com.palehorsestudios.ard.characters.Monster;
import com.palehorsestudios.ard.characters.Player;
import com.palehorsestudios.ard.characters.Wolverine;
import com.palehorsestudios.ard.environment.Room;
import org.junit.Test;

import static com.palehorsestudios.ard.characters.MonsterFactory.*;
import static com.palehorsestudios.ard.combat.WinOrLose.IncreasePlayerLife;
import static org.junit.Assert.*;

public class WinOrLoseTest {

    Room currentRoom = new Room("desc", 3);
    Player player = new Wolverine("Wolverine", 100, currentRoom, currentRoom.getItems(), 1);
    Monster monster = createMonster();

    @Test
    public void levelUpPositiveTest() {
        int expectedLevel = 2;
        int expextedPlayerLife = 120;
        int expectedMonsterStartingLife = 70;

        player.setLevel(player.getLevel() + 1);
        int actualLevel = player.getLevel();

        IncreasePlayerLife(player);
        int actualPlayerLife = player.getLife();

        setMonsterStartingLife(getMonsterStartingLife() + 20);
        int actualMonsterStartingLife = getMonsterStartingLife();

        assertEquals(expectedLevel, actualLevel);
        assertEquals(expextedPlayerLife, actualPlayerLife);
        assertEquals(expectedMonsterStartingLife, actualMonsterStartingLife);
    }
}