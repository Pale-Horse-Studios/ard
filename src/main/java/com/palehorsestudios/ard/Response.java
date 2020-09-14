package com.palehorsestudios.ard;

import lombok.Data;

@Data class Response {
  String response;
  boolean gameOver;
  String gameOverResult;

  Response(Builder builder) {
    this.response = builder.response;
    this.gameOver = builder.gameOver;
    this.gameOverResult = builder.gameOverResult;
  }

  static class Builder {
    String response;
    boolean gameOver;
    String gameOverResult;

    Builder() { }

    Builder response(String response) {
      this.response = response;
      return this;
    }

    Builder gameOver(boolean gameOver) {
      this.gameOver = gameOver;
      return this;
    }

    Builder gameOverResult(String gameOverResult) {
      this.gameOverResult = gameOverResult;
      return this;
    }

    Response build() {
      return new Response(this);
    }
  }
}
