package com.palehorsestudios.ard.characters;

import com.palehorsestudios.ard.environment.Item;
import com.palehorsestudios.ard.environment.Room;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.palehorsestudios.ard.combat.combatEngine.MonsterFightsPlayer;
import static com.palehorsestudios.ard.combat.combatEngine.fightRoomMonster;

public class Wolverine extends Player {
    Monster monster;

    public Wolverine(String name, int life, Room currentRoom, List<Item> itemsInventory, int level) {
        super(name, life, currentRoom, itemsInventory, level);
    }

    @Override
    public String attack() {
        StringBuilder sb = new StringBuilder();
        int rand = ThreadLocalRandom.current().nextInt(2);
        switch (rand) {
            case 0:
                sb.append(fightRoomMonster(this));
                if (getCurrentRoom().getMonsters().size() > 0) {
                    sb.append("\n").append(MonsterFightsPlayer(getCurrentRoom().getMonsters().get(0), this));
                }
                break;
            case 1:
                if (getCurrentRoom().getMonsters().size() > 0) {
                    sb.append(MonsterFightsPlayer(getCurrentRoom().getMonsters().get(0), this));
                }
                if (this.getLife() > 0) {
                    sb.append("\n").append(fightRoomMonster(this));
                }
        }
        return sb.toString();
    }

    @Override //health boost
    public String useSpecialPower() {
        if (this.getItemsInventory().contains(Item.valueOf("Power_stone"))) {
            int lifeValue = getLife();
            lifeValue += 50;
            setLife(lifeValue);
            this.getItemsInventory().remove(Item.valueOf("Power_stone"));
            return this.getName() + " has power stone in inventory and just used special power to self boost health by 50!";
        } else {
            return "Can't use special power without power stone in inventory!";
        }
    }

}
