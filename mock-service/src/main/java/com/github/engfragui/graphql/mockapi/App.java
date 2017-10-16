package com.github.engfragui.graphql.mockapi;

import com.github.engfragui.graphql.mockapi.health.AppHealthCheck;
import com.github.engfragui.graphql.mockapi.routes.AuthorById;
import com.github.engfragui.graphql.mockapi.routes.BookDetailByIsbn;
import com.github.engfragui.graphql.mockapi.routes.ReviewByIsbn;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class App extends Application<AppConfig> {
  private static final Logger LOG = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) throws Exception {
    new App().run(args);
  }

  @Override
  public String getName() {
    return "mock-service-app";
  }

  @Override
  public void initialize(Bootstrap<AppConfig> bootstrap) {
    LOG.info("Initializing...");
    bootstrap.addBundle(new SwaggerBundle<AppConfig>() {
      @Override
      protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(AppConfig appConfig) {
        return appConfig.swaggerBundleConfiguration;
      }
    });
  }

  @Override
  public void run(AppConfig config, Environment env) throws Exception {
    LOG.info("Running...");

    // Enable CORS headers
    final FilterRegistration.Dynamic cors =
        env.servlets().addFilter("CORS", CrossOriginFilter.class);

    // Configure CORS parameters
    cors.setInitParameter("allowedOrigins", "*"); // TODO possibly restrict this to *.trulia.com
    cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
    cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");
    // Add URL mapping
    cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

    // Register Routes
    env.jersey().register(new BookDetailByIsbn());
    env.jersey().register(new AuthorById());
    env.jersey().register(new ReviewByIsbn());

    // Register Health Check
    env.healthChecks().register("mock-service-health-check", new AppHealthCheck());
  }
}