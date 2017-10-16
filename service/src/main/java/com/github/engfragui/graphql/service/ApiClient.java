package com.github.engfragui.graphql.service;

import com.github.engfragui.graphql.api.Response;
import com.github.engfragui.graphql.service.http.ClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

public class ApiClient<T> {
  private static final Logger LOG = LoggerFactory.getLogger(ApiClient.class);

  private final String baseUrl;
  private final Client client;

  public ApiClient(String baseUrl, int connectTimeout, int readTimeout, int maxTotal){
    this.baseUrl = baseUrl;
    this.client = ClientFactory.create(connectTimeout, readTimeout, maxTotal);
  }

  public Response<T> get(String path, GenericType<Response<T>> responseType){

    WebTarget target = client
        .target(baseUrl)
        .path(path);

    Invocation.Builder builder = target
        .request(MediaType.APPLICATION_JSON_TYPE);

    LOG.info("Sending GET query: " + target.getUri().toString());

    Response<T> response = builder.get(responseType);
    // TODO add error handling

    return response;
  }
}
