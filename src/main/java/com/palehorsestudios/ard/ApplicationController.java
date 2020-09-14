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
  Game game;

  @GetMapping("/")
  public String getHome() {
    game = new Game();
    game.newGame();
    
    return "index";
  }

  @GetMapping(path = "/command/{cmd}", produces = "application/json")
  @ResponseBody
  public Response doCommand(@PathVariable String cmd) {
    String[] command = TextParser.parser(cmd);
    switch (command[0]) {
      case "move":
        int size = game.getGameMap().size();
        game.getGameMap().moveCharacter(game.getPlayer(), Direction.valueOf(command[1]));
        game.increaseScore(size);
        break;
      case "look":
        game.Look(game.getPlayer(), command[1]);
        break;
      case "flight":
        game.Flight(game.getPlayer(), command[1]);
        break;
      case "fight":
        game.Fight(game.getPlayer(), command[1]);
        break;
      case "pickup":
        game.getPlayer().pickUpItem(Item.valueOf(command[1]));
        break;
      case "drop":
        game.getPlayer().dropItem(Item.valueOf(command[1]));
        break;
      case "help":
        ConsoleManager.gameExplanation();
        break;
      case "unlock":
        game.unlockChest(game.getPlayer());
        break;
      case "use":
        game.UsePower(game.getPlayer(), command[1]);
    }
    return new Response(cmd);
  }
}
