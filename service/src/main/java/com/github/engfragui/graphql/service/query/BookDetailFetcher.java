package com.github.engfragui.graphql.service.query;

import com.github.engfragui.graphql.api.BookDetail;
import com.github.engfragui.graphql.datasource.factory.BookDetailFactory;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class BookDetailFetcher implements DataFetcher {

  public BookDetailFetcher() {
  }

  @Override
  public Object get(DataFetchingEnvironment env) {

    String fieldName = env.getFields().get(0).getName();
    Object source = env.getSource();

    switch (fieldName) {

      case "book":
        String id = env.getArgument("id");
        return retrieveBook(id);

      case "id":
        return ((BookDetail) source).getId();

      case "isbn":
        return ((BookDetail) source).getIsbn();

      case "title":
        return ((BookDetail) source).getTitle();

      default:
        return source;
    }
  }

  private BookDetail retrieveBook(String id) {
    return BookDetailFactory.getBookDetailByBookId(id);
  }
}
