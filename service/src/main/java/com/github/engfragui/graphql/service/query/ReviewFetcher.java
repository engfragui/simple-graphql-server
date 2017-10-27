package com.github.engfragui.graphql.service.query;

import com.github.engfragui.graphql.api.BookDetail;
import com.github.engfragui.graphql.api.Review;
import com.github.engfragui.graphql.datasource.factory.ReviewFactory;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class ReviewFetcher implements DataFetcher {

  public ReviewFetcher() {
  }

  @Override
  public Object get(DataFetchingEnvironment env) {

    String fieldName = env.getFields().get(0).getName();

    Object source = env.getSource();

    if (source instanceof BookDetail) {

      // this happens only once, the first time this fetcher is called

      final BookDetail bookDetail = (BookDetail) source;
      String bookId = null;

      if (bookDetail.getId() != null) {
        bookId = ((BookDetail) source).getId();
      }

      if (bookId != null) {
        return retrieveReview(bookId);
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

  private Review retrieveReview(String bookId) {
    return ReviewFactory.getReviewByBookId(bookId);
  }
}
