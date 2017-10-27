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

    switch (fieldName) {

      case "review":
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
