package com.mwl.util.commands;

import com.mwl.environment.Item;

import java.util.Arrays;
import java.util.List;

/**
 * Look Class implements the Commands interface to validate user input
 */
public class Look implements Commands {
    @Override
    public void do_command(String option) throws IllegalArgumentException {
     List<String> valid = List.of("Around", "Me");
        if (option == null || !valid.contains(option)
                && !Arrays.stream(Item.values()).anyMatch((items) -> items.name().equals(option)))
            throw new IllegalArgumentException("Look what?");
    }
}