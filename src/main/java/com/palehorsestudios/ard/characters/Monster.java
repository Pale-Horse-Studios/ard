package com.palehorsestudios.ard.characters;

import com.palehorsestudios.ard.environment.Room;
import com.palehorsestudios.ard.util.Codes;
import com.palehorsestudios.ard.util.ConsoleManager;

import java.util.HashMap;
import java.util.Map;

public abstract class Monster {
    private String name;
    private String description;
    private int life;

    private int x;
    private int y;
    //  private Room currentRoom;

    public Monster() {
        // default constructor
    }

    public abstract void attack();

    public abstract void move();

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCoord(Room room) {
        int x;
        int y;
        if (room.getX() - 1 < 0) {
            x = 0;
        } else {
            x = room.getX()-1;
        }

        if (room.getY() - 1 < 0) {
            y = 0;
        } else {
            y = room.getY()-1;
        }
        this.x = ConsoleManager.getRandomInteger(x);
        this.y = ConsoleManager.getRandomInteger(y);
    };

    public Map<String, String> getMonsterInfo() {
        Map<String, String> monsterInfo = new HashMap<>();
        monsterInfo.put("name", this.getName());
        monsterInfo.put("description", this.getDescription());
        int rand = ConsoleManager.getRandomInteger(1, 5);
        if (rand <= 2) {
            monsterInfo.put("type", "monster");
        } else {
            monsterInfo.put("type", "monster2");
        }
        monsterInfo.put("life", String.valueOf(this.getLife()));
        monsterInfo.put("x", String.valueOf(this.getX()));
        monsterInfo.put("y", String.valueOf(this.getY()));

        return monsterInfo;
    }

    @Override
    public String toString() {
        return "Monster{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", life=" + life +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
