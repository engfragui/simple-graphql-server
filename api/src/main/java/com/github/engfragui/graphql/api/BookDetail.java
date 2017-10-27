package com.github.engfragui.graphql.api;

public class BookDetail {

  private String id;
  private String isbn;
  private String title;
  private String authorId;

  public BookDetail(String id, String isbn, String title, String authorId) {
    this.id = id;
    this.isbn = isbn;
    this.title = title;
    this.authorId = authorId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthorId() {
    return authorId;
  }

  public void setAuthorId(String authorId) {
    this.authorId = authorId;
  }
}
