package com.mwl.characters;

public class Normal extends Monster {
    Player player;

    // Constructor
    public Normal(String name, int life, String description) {
        setName(name);
        setDescription(description);
        setLife(life);
        //   setCurrentRoom(currentRoom);
    }

    // Abstract methods with local logic
    @Override
    public void attack() {
    }

    @Override
    public void move() {
        // Add move code
    }

}
