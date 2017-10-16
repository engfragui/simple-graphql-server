package com.github.engfragui.graphql.mockapis.routes;

import com.codahale.metrics.annotation.Timed;
import com.github.engfragui.graphql.api.Response;
import com.github.engfragui.graphql.api.Review;
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

@Path("/api/v1/review")
@Api("Review")
@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
public class ReviewByIsbn {

  public ReviewByIsbn() {
  }

  @GET
  @ApiOperation(value = "Get review by isbn")
  @Timed
  @Path("/{isbn}")
  @CacheControl(maxAge = 1, maxAgeUnit = TimeUnit.DAYS)
  public Response get(@ApiParam(required=true, value="isbn", example="0439708184") @PathParam("isbn") @NotNull final String isbn) {

    Response<Review> response = new Response<>();
    Review review = new Review();

    switch (isbn) {
      case "0439708184":
        review.setId("444");
        review.setTitle("Magical book");
        review.setContent("This book is amazing, I reread it every year");
        break;
        // TODO add more books here
    }

    response.setData(review);
    return response;
  }
}
