package com.palehorsestudios.ard;

import com.palehorsestudios.ard.util.ConsoleManager;
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
    return responseBuilder.response(ConsoleManager.gameIntro()).build();
  }

  @GetMapping(path = "/command/{cmd}", produces = "application/json")
  @ResponseBody
  public Response doCommand(@PathVariable String cmd) {
    return game.play(cmd);
  }

  @GetMapping(path = "/help", produces = "application/json")
  @ResponseBody
  public Response getHelp() {
    return game.help();
  }
}
