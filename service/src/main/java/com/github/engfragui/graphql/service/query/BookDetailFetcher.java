package com.github.engfragui.graphql.service.query;

import com.github.engfragui.graphql.api.BookDetail;
import com.github.engfragui.graphql.datasource.factory.BookDetailFactory;
import graphql.execution.ExecutionId;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class BookDetailFetcher implements DataFetcher {

  private ExecutionId lastExecutionId; // TODO improve this/check if needed

  public BookDetailFetcher() {
  }

  @Override
  public Object get(DataFetchingEnvironment env) {

    String fieldName = env.getFields().get(0).getName();

    Object source = env.getSource();

    // TODO check if this is needed
    if (source instanceof DataFetcher) {
      return ((DataFetcher) source).get(env);
    }

    // TODO check if this is needed
    ExecutionId executionId = env.getExecutionId();

    if (!executionId.equals(lastExecutionId)) {

      lastExecutionId = executionId;

      String isbn = env.getArgument("isbn");

      return retrieveBook(isbn);
    }

    // TODO check this switch
    switch (fieldName) {

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

  private BookDetail retrieveBook(String isbn) {
    return BookDetailFactory.getBookDetailByBookId(isbn);
  }
}
