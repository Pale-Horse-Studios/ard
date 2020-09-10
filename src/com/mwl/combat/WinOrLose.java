package com.mwl.combat;

import com.mwl.characters.Player;
import static com.mwl.characters.MonsterFactory.getMonsterStartingLife;
import static com.mwl.characters.MonsterFactory.setMonsterStartingLife;

public class WinOrLose {

    /**
     * Call this method when monster is defeated to level up player.
     *
     * @param player current game player
     */
    public static void LevelUp(Player player) {
        player.setLevel(player.getLevel() + 1);
        IncreasePlayerLife(player);
        setMonsterStartingLife(getMonsterStartingLife() + 20);
    }

    /**
     * Increment player life
     * package private for test purposes.
     *
     * @param player current player
     */
    static void IncreasePlayerLife(Player player) {
        player.setLife(player.getLife() + 20);
    }

}
