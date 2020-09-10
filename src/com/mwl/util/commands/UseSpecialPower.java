package com.mwl.util.commands;

public class UseSpecialPower implements Commands {

    @Override
    public void do_command(String option) throws IllegalArgumentException {
        if (!option.equals("Power")) {
            throw new IllegalArgumentException("Use what?");
        }
    }
}
