package com.palehorsestudios.ard;

import lombok.Data;

@Data class Response {
  String response;
  boolean gameOver;
  boolean isQuestion;
  boolean characterSelected;
  String gameOverResult;

  Response(Builder builder) {
    this.response = builder.response;
    this.gameOver = builder.gameOver;
    this.gameOverResult = builder.gameOverResult;
    this.isQuestion = builder.isQuestion;
    this.characterSelected = builder.characterSelected;
  }

  static class Builder {
    String response;
    boolean gameOver;
    String gameOverResult;
    boolean isQuestion;
    boolean characterSelected;

    Builder() { }

    Builder response(String response) {
      this.response = response;
      return this;
    }

    Builder gameOver(boolean gameOver) {
      this.gameOver = gameOver;
      return this;
    }

    Builder characterSelected(boolean characterSelected) {
      this.characterSelected = characterSelected;
      return this;
    }

    Builder isQuestion(boolean isQuestion) {
      this.isQuestion = isQuestion;
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
