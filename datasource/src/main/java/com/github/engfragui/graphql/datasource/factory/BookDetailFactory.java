package com.github.engfragui.graphql.datasource.factory;

import com.github.engfragui.graphql.api.BookDetail;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookDetailFactory {

  static private Map<String, BookDetail> bookIdBookDetailMap = new HashMap<>();
  static {
    TsvParserSettings settings = new TsvParserSettings();
    settings.getFormat().setLineSeparator("\n");
    TsvParser parser = new TsvParser(settings);
    List<String[]> rows = parser.parseAll(Thread.currentThread().getContextClassLoader().getResourceAsStream("book_details.tsv"));
    rows.stream().filter(row -> row != null && row.length == 4).forEach(row -> {
      String id = row[0];
      String isbn = row[1];
      String title = row[2];
      String authorId = row[3];
      bookIdBookDetailMap.put(id, new BookDetail(id, isbn, title, authorId));
    });
  }

  public static BookDetail getBookDetailByBookId(String bookId) {
    return bookIdBookDetailMap.get(bookId);
  }

  public static void main(String[] args) {
    BookDetail bookDetail = BookDetailFactory.getBookDetailByBookId("11111");
    System.out.println(bookDetail.getId());
    System.out.println(bookDetail.getIsbn());
    System.out.println(bookDetail.getTitle());
    System.out.println(bookDetail.getAuthorId());
  }
}
