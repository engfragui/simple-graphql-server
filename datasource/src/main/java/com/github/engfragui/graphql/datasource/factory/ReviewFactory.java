package com.github.engfragui.graphql.datasource.factory;

import com.github.engfragui.graphql.api.Review;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewFactory {

  static private Map<String, Review> isbnReviewMap = new HashMap<>();
  static {
    TsvParserSettings settings = new TsvParserSettings();
    settings.getFormat().setLineSeparator("\n");
    TsvParser parser = new TsvParser(settings);
    List<String[]> rows = parser.parseAll(Thread.currentThread().getContextClassLoader().getResourceAsStream("reviews.tsv"));
    rows.stream().filter(row -> row != null && row.length == 5).forEach(row -> {
      String id = row[0];
      String bookId = row[1];
      String isbn = row[2];
      Integer starRating = Integer.parseInt(row[3]);
      String content = row[4];
      isbnReviewMap.put(bookId, new Review(id, bookId, isbn, starRating, content));
    });
  }

  public static Review getReviewByBookId(String bookId) {
    return isbnReviewMap.get(bookId);
  }

  public static void main(String[] args) {
    Review review = ReviewFactory.getReviewByBookId("11111");
    System.out.println(review.getId());
    System.out.println(review.getBookId());
    System.out.println(review.getIsbn());
    System.out.println(review.getStarRating());
    System.out.println(review.getContent());
  }
}
