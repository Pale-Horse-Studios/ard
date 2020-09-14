package com.palehorsestudios.ard.characters;

import com.palehorsestudios.ard.environment.Item;
import com.palehorsestudios.ard.environment.Room;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.palehorsestudios.ard.combat.combatEngine.MonsterFightsPlayer;
import static com.palehorsestudios.ard.combat.combatEngine.fightRoomMonster;

public class Ironman extends Player {
    Monster monster;

    public Ironman(String name, int life, Room currentRoom, List<Item> itemsInventory, int level) {
        super(name, life, currentRoom, itemsInventory, level);
    }

    @Override
    public String attack() {
        StringBuilder sb = new StringBuilder();
        int rand = ThreadLocalRandom.current().nextInt(2);
        if(rand == 0) {
            sb.append(fightRoomMonster(this));
            if (getCurrentRoom().getMonsters().size() > 0) {
                sb.append("\n").append(MonsterFightsPlayer(getCurrentRoom().getMonsters().get(0), this));
            }
        } else {
            if (getCurrentRoom().getMonsters().size() > 0) {
                sb.append(MonsterFightsPlayer(getCurrentRoom().getMonsters().get(0), this));
            }
            sb.append("\n").append(fightRoomMonster(this));
        }
        return sb.toString();
    }

    @Override //generate more items
    public void useSpecialPower() {
        if (this.getItemsInventory().contains(Item.valueOf("Power_stone"))) {
            List<Item> inventory = getItemsInventory();
            int random = (int) (Math.random() * Item.values().length + 1);
            inventory.add(Item.values()[random]);
            inventory.remove(Item.valueOf("Power_stone"));
            System.out.println(this.getName() + " has power stone in inventory and just used special power to generate one more item!");
        } else {
            System.out.println("Can't use special power without power stone in inventory!");
        }
    }
}
