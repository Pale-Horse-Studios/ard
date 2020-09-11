package com.palehorsestudios.ard;

import lombok.Data;

@Data class Response {
  String response;

  public Response(String command) {
    this.response = command;
  }
}
