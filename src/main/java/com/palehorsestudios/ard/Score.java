package com.palehorsestudios.ard;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Score {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  LocalDate date;
  String name;
  String player_character;
  int score;
  int level;

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCharacter() {
    return player_character;
  }

  public void setCharacter(String character) {
    this.player_character = character;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }
}
