package com.mwl.util.commands;

/**
 * Commands interface specify behavior that classes must implement.
 */
public interface Commands {

    /**
     * do_command method verifies if option is valid. Throws IllegalArgumentException if option is not valid
     *
     * @param option is the user input to check against
     */
    void do_command(String option) throws IllegalArgumentException;
}
