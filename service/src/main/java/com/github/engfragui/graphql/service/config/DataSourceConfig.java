package com.github.engfragui.graphql.service.config;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import javax.validation.constraints.NotNull;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class DataSourceConfig {

  @NotNull
  private String baseUrl;

  @NotNull
  private int connectTimeout;

  @NotNull
  private int readTimeout;

  @NotNull
  private int maxTotal;

  public String getBaseUrl() {
    return baseUrl;
  }

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public int getConnectTimeout() {
    return connectTimeout;
  }

  public void setConnectTimeout(int connectTimeout) {
    this.connectTimeout = connectTimeout;
  }

  public int getReadTimeout() {
    return readTimeout;
  }

  public void setReadTimeout(int readTimeout) {
    this.readTimeout = readTimeout;
  }

  public int getMaxTotal() {
    return maxTotal;
  }

  public void setMaxTotal(int maxTotal) {
    this.maxTotal = maxTotal;
  }

  public Client build() {

    ClientConfig clientConfig = new ClientConfig(new JacksonJaxbJsonProvider());
    clientConfig = clientConfig.property(ClientProperties.CONNECT_TIMEOUT, connectTimeout);
    clientConfig = clientConfig.property(ClientProperties.READ_TIMEOUT, readTimeout);

    PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
    connectionManager.setMaxTotal(maxTotal);
    connectionManager.setDefaultMaxPerRoute(maxTotal);

    clientConfig = clientConfig.property(ApacheClientProperties.CONNECTION_MANAGER, connectionManager);

    return ClientBuilder.newClient(clientConfig);
  }
}
