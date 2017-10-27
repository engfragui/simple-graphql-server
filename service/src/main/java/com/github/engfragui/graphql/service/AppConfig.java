package com.github.engfragui.graphql.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smoketurner.dropwizard.graphql.GraphQLFactory;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class AppConfig extends Configuration {

  @JsonProperty("swagger")
  public SwaggerBundleConfiguration swaggerBundleConfiguration;

  @NotNull
  @Valid
  public final GraphQLFactory graphql = new GraphQLFactory();

  @JsonProperty
  public GraphQLFactory getGraphQLFactory() {
    return graphql;
  }
}