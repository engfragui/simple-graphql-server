package com.github.engfragui.graphql.service.query;

import com.github.engfragui.graphql.api.Author;
import com.github.engfragui.graphql.api.BookDetail;
import com.github.engfragui.graphql.api.Response;
import com.github.engfragui.graphql.service.http.DataSourceManager;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.GenericType;

public class AuthorFetcher implements DataFetcher {
  private static final Logger LOG = LoggerFactory.getLogger(AuthorFetcher.class);

  private final DataSourceManager dataSourceManager;

  public AuthorFetcher(DataSourceManager dataSourceManager) {
    this.dataSourceManager = dataSourceManager;
  }

  @Override
  public Object get(DataFetchingEnvironment env) {

    String fieldName = env.getFields().get(0).getName();

    Object source = env.getSource();

    if (source instanceof BookDetail) {

      // this happens only once, the first time this fetcher is called

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
    }

    switch (fieldName) {

      case "id":
        return ((Author) source).getId();

      case "name":
        return ((Author) source).getName();

      default:
        return source;
    }
  }

  // TODO improve this method
  private Author retrieveAuthor(String id) {

    Response<Author> response = dataSourceManager.getAuthorClient().get(id, new GenericType<Response<Author>>(){});

    return response.getData();
  }
}
