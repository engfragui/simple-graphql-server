package com.github.engfragui.graphql.mockapi.routes;

import com.codahale.metrics.annotation.Timed;
import com.github.engfragui.graphql.api.Author;
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

@Path("/api/v1/author")
@Api("Author")
@Produces(MediaType.APPLICATION_JSON + "; charset=utf-8")
public class AuthorById {

  public AuthorById() {
  }

  @GET
  @ApiOperation(value = "Get author by id")
  @Timed
  @Path("/{id}")
  @CacheControl(maxAge = 1, maxAgeUnit = TimeUnit.DAYS)
  public Response get(@ApiParam(required=true, value="id", example="2222") @PathParam("id") @NotNull final String id) {

    Response<Author> response = new Response<>();
    Author author = new Author();
    author.setId(id);

    switch (id) {
      case "2222":
        author.setName("J.K. Rowling");
        break;
        // TODO add more authors here
    }

    response.setData(author);
    return response;
  }
}
