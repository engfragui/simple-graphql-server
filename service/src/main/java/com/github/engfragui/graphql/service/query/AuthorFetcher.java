package com.github.engfragui.graphql.service.query;

import com.github.engfragui.graphql.api.Author;
import com.github.engfragui.graphql.api.BookDetail;
import com.github.engfragui.graphql.datasource.factory.AuthorFactory;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class AuthorFetcher implements DataFetcher {

  public AuthorFetcher() {
  }

  @Override
  public Object get(DataFetchingEnvironment env) {

    String fieldName = env.getFields().get(0).getName();
    Object source = env.getSource();

    switch (fieldName) {

      case "author":
        final BookDetail bookDetail = (BookDetail) source;
        String authorId = null;

        if (bookDetail.getAuthorId() != null) {
          authorId = ((BookDetail) source).getAuthorId();
        }

        if (authorId != null) {
          return retrieveAuthor(authorId);
        } else { // we were not able to retrieve an authorId for the book
          return null;
        }

      case "id":
        return ((Author) source).getId();

      case "name":
        return ((Author) source).getName();

      default:
        return source;
    }
  }

  private Author retrieveAuthor(String id) {
    return AuthorFactory.getAuthorByAuthorId(id);
  }
}
