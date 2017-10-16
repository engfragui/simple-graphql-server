package com.github.engfragui.graphql.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.engfragui.graphql.service.config.DataSourceConfig;
import com.smoketurner.dropwizard.graphql.GraphQLFactory;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class AppConfig extends Configuration {

  private DataSourceConfig bookDetailDataSourceConfig;
  private DataSourceConfig authorDataSourceConfig;
  private DataSourceConfig reviewDataSourceConfig;

  @JsonProperty("bookDetailDataSourceConfig")
  public DataSourceConfig getBookDetailDataSourceConfig() {
    return bookDetailDataSourceConfig;
  }

  @JsonProperty("authorDataSourceConfig")
  public DataSourceConfig getAuthorDataSourceConfig() {
    return authorDataSourceConfig;
  }

  @JsonProperty("reviewDataSourceConfig")
  public DataSourceConfig getReviewDataSourceConfig() {
    return reviewDataSourceConfig;
  }

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