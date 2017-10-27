package com.github.engfragui.graphql.api;

public class Review {

  private String id;
  private String bookId;
  private String isbn;
  private Integer starRating;
  private String content;

  public Review(String id, String bookId, String isbn, Integer starRating, String content) {
    this.id = id;
    this.bookId = bookId;
    this.isbn = isbn;
    this.starRating = starRating;
    this.content = content;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getBookId() {
    return bookId;
  }

  public void setBookId(String bookId) {
    this.bookId = bookId;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public Integer getStarRating() {
    return starRating;
  }

  public void setStarRating(Integer starRating) {
    this.starRating = starRating;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
