package com.github.engfragui.graphql.service;

import com.github.engfragui.graphql.service.health.AppHealthCheck;
import com.github.engfragui.graphql.service.query.SchemaProvider;
import com.smoketurner.dropwizard.graphql.GraphQLBundle;
import com.smoketurner.dropwizard.graphql.GraphQLFactory;
import graphql.servlet.OsgiGraphQLServlet;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
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
    return "simple-graphql-server-app";
  }

  @Override
  public void initialize(Bootstrap<AppConfig> bootstrap) {
    bootstrap.addBundle(new GraphQLBundle<AppConfig>() {
      @Override
      public GraphQLFactory getGraphQLFactory(
          AppConfig configuration) {
        return configuration.getGraphQLFactory();
      }
    });
    bootstrap.addBundle(new AssetsBundle("/assets/", "/graphiql"));
  }

  @Override
  public void run(AppConfig config, Environment env) throws Exception {
    LOG.info("Running...");

    // Enable CORS headers
    final FilterRegistration.Dynamic cors =
        env.servlets().addFilter("CORS", CrossOriginFilter.class);

    // Configure CORS parameters
    cors.setInitParameter("allowedOrigins", "*");
    cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
    cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");
    // Add URL mapping
    cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

    final SchemaProvider provider = new SchemaProvider();

    final OsgiGraphQLServlet servlet = (OsgiGraphQLServlet) config.getGraphQLFactory().build();
    servlet.bindQueryProvider(provider);

    // Register Health Check
    env.healthChecks().register("simple-graphql-server-health-check", new AppHealthCheck());
  }
}
