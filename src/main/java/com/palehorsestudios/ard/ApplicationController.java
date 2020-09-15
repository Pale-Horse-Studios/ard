package com.palehorsestudios.ard;

import com.palehorsestudios.ard.characters.PlayerFactory;
import com.palehorsestudios.ard.util.ConsoleManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
public class ApplicationController {
  Game game;
  boolean isPlayerInitiated = false;

  @GetMapping("/")
  public String getHome() {
    game = new Game();
    return "index";
  }

  @GetMapping(path = "/intro", produces = "application/json")
  @ResponseBody
  public Response getIntro() {
    Response.Builder responseBuilder = new Response.Builder();
    responseBuilder.characterSelected(true);
    return responseBuilder.response(ConsoleManager.gameIntro()).build();
  }

  @GetMapping(path = "/command/{cmd}", produces = "application/json")
  @ResponseBody
  public Response doCommand(@PathVariable String cmd) {
    return game.play(cmd);
  }

  @GetMapping(path = "/character/{character}", produces = "application/json")
  @ResponseBody
  public Response selectCharacter(@PathVariable String character) {
    game.setPlayer(PlayerFactory.createPlayer(game.getGameMap().getStart(), new ArrayList<>(), character));
    Response.Builder responseBuilder = new Response.Builder();
    responseBuilder.response(game.getPlayer().toString());
    return responseBuilder.build();
  }
}
