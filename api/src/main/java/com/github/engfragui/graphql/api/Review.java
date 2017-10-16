package com.github.engfragui.graphql.api;

public class Review {

  private String id;
  private StarRatingEnum starRating;
  private String content;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public StarRatingEnum getStarRating() {
    return starRating;
  }

  public void setStarRating(StarRatingEnum starRating) {
    this.starRating = starRating;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
