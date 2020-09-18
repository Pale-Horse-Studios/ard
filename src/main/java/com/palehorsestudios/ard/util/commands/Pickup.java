package com.palehorsestudios.ard.util.commands;

import com.palehorsestudios.ard.environment.Item;
import com.palehorsestudios.ard.environment.Room;

import java.util.Arrays;

/**
 * Pickup Class implements the Commands interface and throws exception if user input is not valid
 */
public class Pickup implements Commands {
    @Override
    public void do_command(String option) throws IllegalArgumentException {
        if (!Arrays.stream(Item.values()).anyMatch((items) -> items.name().equals(option)) || !Room.ALL.contains(option.toLowerCase()))
            throw new IllegalArgumentException("pickup what?");
    }
}
