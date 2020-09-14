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
  boolean isPlayerInitiated = false;

  @GetMapping("/")
  public String getHome() {
    game = new Game();
    game.newGame();
    System.out.println("returning index");
    return "index";
  }

  @GetMapping(path = "/intro", produces = "application/json")
  @ResponseBody
  public Response getIntro() {
    Response.Builder responseBuilder = new Response.Builder();
    System.out.println("returning intro");
    return responseBuilder.response("test intro").build();
  }

  @GetMapping(path = "/command/{cmd}", produces = "application/json")
  @ResponseBody
  public Response doCommand(@PathVariable String cmd) {
    System.out.println("in doCommand");
    return game.play(cmd);
  }
}
