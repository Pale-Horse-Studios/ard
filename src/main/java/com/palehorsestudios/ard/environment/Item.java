package com.palehorsestudios.ard.environment;

import com.palehorsestudios.ard.util.ConsoleManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Item {
    // basic items
    Sword("Bladed melee weapon intended for cutting."),
    Food("Material consisting of protein, carbohydrate, and fat."),
    Milk("Opaque white fluid rich in fat and protein."),
    Shield("Broad piece of metal or another suitable material."),
    Medicine("A compound used for the treatment of disease."),
    Gas_mask("Covers face as a defense against poisonous gas"),
    //upgraded items
    Space_stone("Represents the element of space."),
    Reality_stone("RepresentS the fabric of reality."),
    Power_stone("Weapon for granting a person with great, cosmic power."),
    Mind_stone("governs over the fabric of mind."),
    Time_stone("It has the ability to manipulate time."),
    Soul_stone("A soul for a soul.");

    private String description;
    private int x;
    private int y;

    Item(String description) {
        this.description = description;
    }

    void setCoord(Room room) {
        int x;
        int y;
        if (room.getX() - 1 < 0) {
            x = 0;
        } else {
            x = room.getX() - 1;
        }

        if (room.getY() - 1 < 0) {
            y = 0;
        } else {
            y = room.getY() - 1;
        }
        this.x = ConsoleManager.getRandomInteger(x);
        this.y = ConsoleManager.getRandomInteger(y);
    };

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, String> getItemInfo() {
        Map<String, String> itemInfo = new HashMap<>();
        itemInfo.put("name", this.name());
        itemInfo.put("description", this.getDescription());
        itemInfo.put("type", "item");
        itemInfo.put("x", String.valueOf(this.getX()));
        itemInfo.put("y", String.valueOf(this.getY()));

        return itemInfo;
    }

    @Override
    public String toString() {
        return this.name();
    }
}