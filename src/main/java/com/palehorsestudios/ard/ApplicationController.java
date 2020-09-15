package com.palehorsestudios.ard;

import com.palehorsestudios.ard.characters.PlayerFactory;
import com.palehorsestudios.ard.util.ConsoleManager;
import com.palehorsestudios.ard.util.InvalidInputException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

import static com.palehorsestudios.ard.util.InputValidation.VALIDATE_CHARACTER_SELECTION;

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
    Response.Builder responseBuilder = new Response.Builder();
    // DONE: Needs character input validation
    try {
      String selection = VALIDATE_CHARACTER_SELECTION(character);

      game.setPlayer(PlayerFactory.createPlayer(game.getGameMap().getStart(), new ArrayList<>(), selection));
      responseBuilder.response(game.getPlayer().getName().concat(" is a great choice."));
    } catch (InvalidInputException e) {
      responseBuilder.characterSelected(true);
      responseBuilder.response(e.getMessage());
    }

    return responseBuilder.build();
  }
}
