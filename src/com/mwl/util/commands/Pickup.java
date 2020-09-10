package com.mwl.util.commands;

import com.mwl.environment.Item;

import java.util.Arrays;

/**
 * Pickup Class implements the Commands interface and throws exception if user input is not valid
 */
public class Pickup implements Commands {
    @Override
    public void do_command(String option) throws IllegalArgumentException {
        if (!Arrays.stream(Item.values()).anyMatch((items) -> items.name().equals(option)))
            throw new IllegalArgumentException("pickup what?");
    }
}
