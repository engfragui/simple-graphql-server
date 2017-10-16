package com.github.engfragui.graphql.mockapi.routes;

import com.codahale.metrics.annotation.Timed;
import com.github.engfragui.graphql.api.Response;
import com.github.engfragui.graphql.api.Review;
import com.github.engfragui.graphql.api.StarRatingEnum;
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

    // TODO move the data into some external data source/database/file
    switch (isbn) {
      case "0439708184":
        review.setId("111111");
        review.setStarRating(StarRatingEnum.FIVE_STARS);
        review.setContent("Re-reading it as an adult didn't make me love this book any less. " +
            "Such a wonderful adventure of friendship, respect, courage, and magic.");
        break;
      case "0375831002":
        review.setId("222222");
        review.setStarRating(StarRatingEnum.FIVE_STARS);
        review.setContent("I don't think I will ever read a book that has such an impact on me as this one has. " +
            "I truly adore this book more and more each time I read it. If I had to pick one book out of the hundreds " +
            "I've read to be my favourite, this one would definitely be it.");
        break;
      case "0545010225":
        review.setId("333333");
        review.setStarRating(StarRatingEnum.FIVE_STARS);
        review.setContent("This was a reread for me (obviously) and it was so heartwarming and perfect. " +
            "Rereading Harry Potter is like coming home. I listened to it on audio because Jim Dale is bae." +
            "Seriously, his Hagrid voice is my life.");
        break;
      case "0739346806":
        review.setId("444444");
        review.setStarRating(StarRatingEnum.FOUR_STARS);
        review.setContent("Amazing, I loved it! Not a super fast-paced book, but such with a creepy, twisted, disturbing, " +
            "awesome vibe throughout it all that makes you want to read (in my case, listen) and do nothing else. " +
            "The twist at the end was incredible, wow! I want more.");
        break;
      case "0307588378":
        review.setId("555555");
        review.setStarRating(StarRatingEnum.FIVE_STARS);
        review.setContent("So addicting. So full of twists and turns that you just cannot stop reading, " +
            "you want to know more, you hope your morning commute would be longer so you could read more. " +
            "The entire storyline was so well crafted (if you read the book you know what I'm talking about).");
        break;
      case "0307341569":
        review.setId("666666");
        review.setStarRating(StarRatingEnum.FOUR_STARS);
        review.setContent("I have now officially read all of Gillian Flynn's books, and I really enjoyed this one! " +
            "The story and the characters were so twisted, and the last hour or so of the audiobook was insaaaane.");
        break;
    }

    response.setData(review);

    // TODO possibly return not found if no review was found
    return response;
  }
}
