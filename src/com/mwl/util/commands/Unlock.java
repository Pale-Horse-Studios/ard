package com.mwl.util.commands;

public class Unlock implements Commands {
    @Override
    public void do_command(String option) throws IllegalArgumentException {
        if (option == null || !option.equals("Chest")) {
            throw new IllegalArgumentException("Unlock what?");
        }
    }
}
