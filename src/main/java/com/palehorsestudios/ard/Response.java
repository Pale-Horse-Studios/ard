package com.palehorsestudios.ard;

import lombok.Data;
import java.util.Map;

@Data class Response {
  String response;
  String banner;
  boolean gameOver;
  boolean isQuestion;
  boolean characterSelected;
  String gameOverResult;
  Map<String, String> playerInfo;
  Map<String, String> roomInfo;

  Response(Builder builder) {
    this.response = builder.response;
    this.banner = builder.banner;
    this.gameOver = builder.gameOver;
    this.gameOverResult = builder.gameOverResult;
    this.isQuestion = builder.isQuestion;
    this.characterSelected = builder.characterSelected;
    this.playerInfo = builder.playerInfo;
    this.roomInfo = builder.roomInfo;
  }

  static class Builder {
    String response;
    String banner;
    boolean gameOver;
    String gameOverResult;
    boolean isQuestion;
    boolean characterSelected;
    Map<String, String> playerInfo;
    Map<String, String> roomInfo;

    Builder() { }

    Builder response(String response) {
      this.response = response;
      return this;
    }

    Builder banner(String banner) {
      this.banner = banner;
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

    Builder playerInfo(Map<String, String> playerInfo) {
      this.playerInfo = playerInfo;
      return this;
    }

    Builder roomInfo(Map<String, String> roomInfo) {
      this.roomInfo = roomInfo;
      return this;
    }

    Response build() {
      return new Response(this);
    }
  }
}
