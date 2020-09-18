package com.palehorsestudios.ard;

import com.palehorsestudios.ard.characters.PlayerFactory;
import com.palehorsestudios.ard.util.ConsoleManager;
import com.palehorsestudios.ard.util.InvalidInputException;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.palehorsestudios.ard.util.InputValidation.VALIDATE_CHARACTER_SELECTION;

@Controller
public class ApplicationController {
  private Game game;

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
    responseBuilder.banner(ConsoleManager.banner());
    return responseBuilder.response(ConsoleManager.gameIntro()).build();
  }

  @GetMapping(path = "/stat", produces = "application/json")
  @ResponseBody
  public Response getStatus() {
    Response.Builder responseBuilder = new Response.Builder();
    responseBuilder.playerInfo(game.getPlayer().getPlayerInfo());
    responseBuilder.roomInfo(game.getPlayer().getCurrentRoom().getRoomInfo());

    return responseBuilder.build();
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

  @GetMapping(path = "/answer/{answer}", produces = "application/json")
  @ResponseBody
  public Response evaluateAnswer(@PathVariable String answer) {
    Response.Builder responseBuilder = new Response.Builder();
    responseBuilder.response(game.getPlayer().getCurrentRoom().submitAnswer(answer));
    return responseBuilder.build();
  }

  @GetMapping(path = "/score/{name}", produces = "application/json")
  @ResponseBody
  public Response keepScore(@PathVariable String name) {
    Response.Builder responseBuilder = new Response.Builder();
    responseBuilder.response(Game.keepScores(name, game.getPlayer()));
    return responseBuilder.build();
  }

  @GetMapping(path = "/playAgain")
  public RedirectView playAgain() {
    return new RedirectView("/");
  }

  @GetMapping(path = "/quit")
  public RedirectView redirectToAmazon() {
    return new RedirectView("https://www.amazon.com");
  }
}
