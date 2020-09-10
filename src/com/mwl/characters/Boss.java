package com.mwl.characters;

public class Boss extends Monster {

    public Boss(String name, int life, String description) {
        setName(name);
        setDescription(description);
        setLife(life);
        //       setCurrentRoom(currentRoom);
    }

    // Abstract methods with local logic
    @Override
    public void attack() {
        // Add attack code
    }

    @Override
    public void move() {
        // Add move code
    }

}
