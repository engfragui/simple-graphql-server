package com.github.engfragui.graphql.mockapi.routes;

import com.codahale.metrics.annotation.Timed;
import com.github.engfragui.graphql.api.BookDetail;
import com.github.engfragui.graphql.api.Response;
import io.dropwizard.jersey.caching.CacheControl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.TimeUnit;

@Path("/api/v1/book")
@Api("Book Detail")
@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
public class BookDetailByIsbn {

  public BookDetailByIsbn() {
  }

  @GET
  @ApiOperation(value = "Get book detail by isbn")
  @Timed
  @Path("/{isbn}")
  @CacheControl(maxAge = 1, maxAgeUnit = TimeUnit.DAYS)
  public Response get(@ApiParam(required=true, value="isbn", example="0439708184") @PathParam("isbn") @NotNull final String isbn) {

    Response<BookDetail> response = new Response<>();
    BookDetail bookDetail = new BookDetail();

    // TODO move the data into some external data source/database/file
    switch (isbn) {
      case "0439708184":
        bookDetail.setId("11111");
        bookDetail.setIsbn(isbn);
        bookDetail.setTitle("Harry Potter and the Sorcerer's Stone");
        bookDetail.setAuthorId("2222");
        break;
      case "0375831002":
        bookDetail.setId("22222");
        bookDetail.setIsbn(isbn);
        bookDetail.setTitle("The Book Thief");
        bookDetail.setAuthorId("1111");
        break;
      case "0545010225":
        bookDetail.setId("33333");
        bookDetail.setIsbn(isbn);
        bookDetail.setTitle("Harry Potter and the Deathly Hallows");
        bookDetail.setAuthorId("2222");
        break;
      case "0739346806":
        bookDetail.setId("44444");
        bookDetail.setIsbn(isbn);
        bookDetail.setTitle("Sharp Objects");
        bookDetail.setAuthorId("3333");
        break;
      case "0307588378":
        bookDetail.setId("55555");
        bookDetail.setIsbn(isbn);
        bookDetail.setTitle("Gone Girl");
        bookDetail.setAuthorId("3333");
        break;
      case "0307341569":
        bookDetail.setId("66666");
        bookDetail.setIsbn(isbn);
        bookDetail.setTitle("Dark Places");
        bookDetail.setAuthorId("3333");
        break;
    }

    response.setData(bookDetail);

    // TODO possibly return not found if the book was not found
    return response;
  }
}
