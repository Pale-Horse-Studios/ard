package com.mwl.util.commands;

import com.mwl.environment.Direction;

import java.util.Arrays;

/**
 * Move Class implements the Commands interface and throws exception if user input is not valid.
 */
public class Move implements Commands {
    @Override
    public void do_command(String direction) throws IllegalArgumentException {
        // Check if the argument direction do not match a direction in the Direction enum
        if (!Arrays.stream(Direction.values()).anyMatch((directions) -> directions.name().equals(direction)))
            // throw exception if not match found. Catch by parser() method.
            throw new IllegalArgumentException("Move where?");
    }
}
