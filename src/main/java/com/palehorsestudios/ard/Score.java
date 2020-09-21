package com.palehorsestudios.ard;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
class Score {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  LocalDate date;
  String name;
  String player_character;
  int score;
  int level;

  LocalDate getDate() {
    return date;
  }

  void setDate(LocalDate date) {
    this.date = date;
  }

  String getName() {
    return name;
  }

  void setName(String name) {
    this.name = name;
  }

  String getCharacter() {
    return player_character;
  }

  void setCharacter(String character) {
    this.player_character = character;
  }

  int getScore() {
    return score;
  }

  void setScore(int score) {
    this.score = score;
  }

  int getLevel() {
    return level;
  }

  void setLevel(int level) {
    this.level = level;
  }
}
