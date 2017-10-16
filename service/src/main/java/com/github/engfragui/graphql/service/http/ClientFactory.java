package com.github.engfragui.graphql.service.http;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class ClientFactory {
  private static final Logger LOG = LoggerFactory.getLogger(ClientFactory.class);

  public static Client create(int connectTimeout, int readTimeout, int maxTotal){

    JacksonJsonProvider jacksonJsonProvider = new JacksonJaxbJsonProvider()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    ClientConfig clientConfig = new ClientConfig(jacksonJsonProvider);
    clientConfig = clientConfig.property(ClientProperties.CONNECT_TIMEOUT, connectTimeout);
    clientConfig = clientConfig.property(ClientProperties.READ_TIMEOUT, readTimeout);

    PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
    connectionManager.setMaxTotal(maxTotal);
    connectionManager.setDefaultMaxPerRoute(maxTotal);

    clientConfig = clientConfig.property(ApacheClientProperties.CONNECTION_MANAGER, connectionManager);

    return ClientBuilder.newClient(clientConfig);
  }
}
