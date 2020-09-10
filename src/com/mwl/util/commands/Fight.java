package com.mwl.util.commands;

/**
 * Fight Class implements Commands interface and throws exception if invalid option
 */
public class Fight implements Commands {
    @Override
    public void do_command(String option) throws IllegalArgumentException {
        if (!option.equals("Monster"))
            throw new IllegalArgumentException("Fight who?");
    }
}
