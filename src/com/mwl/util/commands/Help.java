package com.mwl.util.commands;

/**
 * Help Class implements Commands interface and validate user option
 */
public class Help implements Commands {

    @Override
    public void do_command(String option) throws IllegalArgumentException {
        // Check if the argument word do not match
        if (!option.equals("Me")) {
            throw new IllegalArgumentException("Invalid command. Try again!");
        }
    }
}
