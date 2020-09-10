package com.mwl.util.commands;

/**
 * Flight Class implements Commands and throws exception if option is not specified
 */
public class Flight implements Commands {
    @Override
    public void do_command(String option) throws IllegalArgumentException {
        if (option == null)
            throw new IllegalArgumentException("Flight where?");
    }
}
