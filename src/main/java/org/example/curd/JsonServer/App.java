package org.example.curd.JsonServer;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class App extends Application<App.JsonServerConfiguration> {
    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public void initialize(Bootstrap<JsonServerConfiguration> bootstrap) {

    }

    @Override
    public void run(JsonServerConfiguration configuration, Environment environment) {
        final JsonResource resource = new JsonResource();
        environment.jersey().register(resource);
    }

    public static class JsonServerConfiguration extends Configuration {
        // Any configuration properties can go here
    }
}
