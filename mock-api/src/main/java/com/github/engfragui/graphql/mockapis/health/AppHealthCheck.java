package com.github.engfragui.graphql.mockapis.health;

public class AppHealthCheck extends com.codahale.metrics.health.HealthCheck {

    public AppHealthCheck() {
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
