package com.mwl.util;

import com.mwl.util.commands.*;

import java.util.HashMap;
import java.util.Map;

/**
 * TextParser Class integrates Scanner input located in Console manager with Parser,
 * and commands Classes locate in subpackage implementing Commands interface.
 */
public class TextParser {
    public static String[] parser() {
        Map<String, Commands> commands = new HashMap<>();
        commands.put("pickup", new Pickup());
        commands.put("drop", new Drop());
        commands.put("move", new Move());
        commands.put("flight", new Flight());
        commands.put("look", new Look());
        commands.put("fight", new Fight());
        commands.put("help", new Help());
        commands.put("unlock", new Unlock());
        commands.put("use", new UseSpecialPower());
        String str = ConsoleManager.scanner().nextLine();
        return ConsoleManager.scanInput(commands, str);
    }
}