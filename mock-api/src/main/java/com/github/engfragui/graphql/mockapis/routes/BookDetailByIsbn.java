package com.github.engfragui.graphql.mockapis.routes;

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
    bookDetail.setIsbn(isbn);

    switch (isbn) {
      case "0439708184":
        bookDetail.setId("1111");
        bookDetail.setTitle("Harry Potter and the Sorcerer's Stone");
        bookDetail.setAuthorId("2222");
        break;
        // TODO add more books here
    }

    response.setData(bookDetail);
    return response;
  }
}
