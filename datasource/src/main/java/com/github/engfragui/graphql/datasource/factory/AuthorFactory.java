package com.github.engfragui.graphql.datasource.factory;

import com.github.engfragui.graphql.api.Author;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthorFactory {

  static private Map<String, Author> authorIdAuthorMap = new HashMap<>();
  static {
    TsvParserSettings settings = new TsvParserSettings();
    settings.getFormat().setLineSeparator("\n");
    TsvParser parser = new TsvParser(settings);
    List<String[]> rows = parser.parseAll(Thread.currentThread().getContextClassLoader().getResourceAsStream("authors.tsv"));
    rows.stream().filter(row -> row != null && row.length == 2).forEach(row -> {
      String authorId = row[0];
      String authorName = row[1];
      authorIdAuthorMap.put(authorId, new Author(authorId, authorName));
    });
  }

  public static Author getAuthorByAuthorId(String authorId) {
    return authorIdAuthorMap.get(authorId);
  }

  public static void main(String[] args) {
    Author author = AuthorFactory.getAuthorByAuthorId("1111");
    System.out.println(author.getId());
    System.out.println(author.getName());
  }
}
