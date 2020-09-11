package com.palehorsestudios.ard;

import com.palehorsestudios.ard.environment.Direction;
import com.palehorsestudios.ard.environment.Item;
import com.palehorsestudios.ard.util.ConsoleManager;
import com.palehorsestudios.ard.util.TextParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApplicationController {
  @GetMapping("/")
  public String getHome() {
    return "index";
  }

  @GetMapping(path = "/command/{command}", produces = "application/json")
  @ResponseBody
  public Response doCommand(@PathVariable String command) {
    // Text parser
    String[] command = TextParser.parser();
    // do that thing
    switch (command[0]) {
      case "move":
        int size = gameMap.size();
        gameMap.moveCharacter(player, Direction.valueOf(command[1]));
        increaseScore(size);
        break;
      case "look":
        Look(player, command[1]);
        break;
      case "flight":
        Flight(player, command[1]);
        break;
      case "fight":
        Fight(player, command[1]);
        break;
      case "pickup":
        player.pickUpItem(Item.valueOf(command[1]));
        break;
      case "drop":
        player.dropItem(Item.valueOf(command[1]));
        break;
      case "help":
        ConsoleManager.gameExplanation();
        break;
      case "unlock":
        unlockChest(player);
        break;
      case "use":
        UsePower(player, command[1]);
    }
    return new Response(command);
  }
}
