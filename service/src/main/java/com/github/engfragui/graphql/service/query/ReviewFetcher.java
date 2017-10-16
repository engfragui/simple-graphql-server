package com.github.engfragui.graphql.service.query;

import com.github.engfragui.graphql.api.BookDetail;
import com.github.engfragui.graphql.api.Response;
import com.github.engfragui.graphql.api.Review;
import com.github.engfragui.graphql.service.http.DataSourceManager;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.GenericType;

public class ReviewFetcher implements DataFetcher {
  private static final Logger LOG = LoggerFactory.getLogger(ReviewFetcher.class);

  private final DataSourceManager dataSourceManager;

  public ReviewFetcher(DataSourceManager dataSourceManager) {
    this.dataSourceManager = dataSourceManager;
  }

  @Override
  public Object get(DataFetchingEnvironment env) {

    String fieldName = env.getFields().get(0).getName();

    Object source = env.getSource();

    if (source instanceof BookDetail) {

      // this happens only once, the first time this fetcher is called

      final BookDetail bookDetail = (BookDetail) source;
      String isbn = null;

      if (bookDetail.getIsbn() != null) {
        isbn = ((BookDetail) source).getIsbn();
      }

      if (isbn != null) {
        return retrieveReview(isbn);
      } else { // we were not able to retrieve an isbn for the book
        return null;
      }
    }

    // TODO possibly investigate how to return multiple reviews
    switch (fieldName) {

      case "id":
        return ((Review) source).getId();

      case "stars":
        return ((Review) source).getStarRating();

      case "content":
        return ((Review) source).getContent();

      default:
        return source;
    }
  }

  // TODO improve this method
  private Review retrieveReview(String isbn) {

    Response<Review> response = dataSourceManager.getReviewClient().get(isbn, new GenericType<Response<Review>>(){});

    return response.getData();
  }
}
