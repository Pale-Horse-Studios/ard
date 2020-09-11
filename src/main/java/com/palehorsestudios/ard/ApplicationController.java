package com.palehorsestudios.ard;

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
    return new Response(command);
  }
}
