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
    return "index";
  }

  @GetMapping(path = "/intro", produces = "application/json")
  @ResponseBody
  public Response getIntro() {
    Response.Builder responseBuilder = new Response.Builder();
    return responseBuilder.response("test intro").build();
  }

  @GetMapping(path = "/command/{cmd}", produces = "application/json")
  @ResponseBody
  public Response doCommand(@PathVariable String cmd) {
    return game.play(cmd);
  }
}
