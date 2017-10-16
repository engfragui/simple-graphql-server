package com.github.engfragui.graphql.service.http;

import com.github.engfragui.graphql.service.ApiClient;
import com.github.engfragui.graphql.service.AppConfig;
import io.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceManager implements Managed {
  private static final Logger LOG = LoggerFactory.getLogger(DataSourceManager.class);

  private ApiClient bookDetailClient;
  private ApiClient authorClient;
  private ApiClient reviewClient;

  public DataSourceManager(AppConfig config) throws Exception {

    this.bookDetailClient = new ApiClient(
        config.getBookDetailDataSourceConfig().getBaseUrl(),
        config.getBookDetailDataSourceConfig().getConnectTimeout(),
        config.getBookDetailDataSourceConfig().getReadTimeout(),
        config.getBookDetailDataSourceConfig().getMaxTotal()
    );

    this.authorClient = new ApiClient(
        config.getAuthorDataSourceConfig().getBaseUrl(),
        config.getAuthorDataSourceConfig().getConnectTimeout(),
        config.getAuthorDataSourceConfig().getReadTimeout(),
        config.getAuthorDataSourceConfig().getMaxTotal()
    );

    this.reviewClient = new ApiClient(
        config.getReviewDataSourceConfig().getBaseUrl(),
        config.getReviewDataSourceConfig().getConnectTimeout(),
        config.getReviewDataSourceConfig().getReadTimeout(),
        config.getReviewDataSourceConfig().getMaxTotal()
    );
  }

  public void start() throws Exception {
    // TODO implement method
  }

  public void stop() throws Exception {
    // TODO implement method
  }

  public ApiClient getBookDetailClient() {
    return bookDetailClient;
  }

  public ApiClient getAuthorClient() {
    return authorClient;
  }

  public ApiClient getReviewClient() {
    return reviewClient;
  }
}

