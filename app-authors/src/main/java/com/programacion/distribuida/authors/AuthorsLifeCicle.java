package com.programacion.distribuida.authors;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.vertx.core.Vertx;
import io.vertx.ext.consul.CheckOptions;
import io.vertx.ext.consul.ConsulClient;
import io.vertx.ext.consul.ConsulClientOptions;
import io.vertx.ext.consul.ServiceOptions;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.InetAddress;
import java.util.List;

@ApplicationScoped
public class AuthorsLifeCicle {

    @Inject
    @ConfigProperty(name = "consul.host", defaultValue = "127.0.0.1")
    String consultHost;

    @Inject
    @ConfigProperty(name = "consul.port", defaultValue = "8500")
    Integer consultPort;

    @Inject
    @ConfigProperty(name = "quarkus.http.port", defaultValue = "8070")
    Integer appPort;

    String serviceId;

    public void init(@Observes StartupEvent event, Vertx vertex) {
        System.out.println("authors-lifecycle: init");
        try {
            ConsulClientOptions option = new ConsulClientOptions()
                    .setHost(consultHost)
                    .setPort(consultPort);

            ConsulClient client = ConsulClient.create(vertex, option);

            String ipAddress = InetAddress.getLocalHost().getHostAddress();
            serviceId = "app-authors-%s:%d".formatted(ipAddress, appPort);

            var urlCheck = "http://%s:%d/ping".formatted(ipAddress, appPort);
            CheckOptions checkOptions = new CheckOptions()
                    .setHttp(urlCheck)
                    .setInterval("10s")
                    .setDeregisterAfter("10s");

            var tags = List.of(
                    "traefik.enable=true",
                    "traefik.http.routers.router-app-authors.rule=PathPrefix(`/app-authors`)",
                    "traefik.http.routers.router-app-authors.middlewares=middleware-authors",
                    "traefik.http.middlewares.middleware-authors.stripprefix.prefixes=/app-authors"
            );

            ServiceOptions serviceOptions = new ServiceOptions()
                    .setName("app-authors")
                    .setId(serviceId)
                    .setAddress(ipAddress)
                    .setPort(appPort)
                    .setCheckOptions(checkOptions)
                    .setTags(tags);

            client.registerService(serviceOptions)
                    .onSuccess(it -> System.out.println("authors-lifecycle: Author Service registred in Consul with ID:  " + serviceId))
                    .onFailure(it -> {
                        System.out.println("Failed to register Authors Service in Consul :  " + it.getMessage());
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy(@Observes ShutdownEvent event, Vertx vertex) {
        System.out.println("authors-lifecycle: destroy");
        ConsulClientOptions option = new ConsulClientOptions()
                .setHost(consultHost)
                .setPort(consultPort);
        ConsulClient client = ConsulClient.create(vertex, option);

        client.deregisterService(serviceId)
                .onSuccess(it -> System.out.println("Authors service deregistered from consul ID: " + serviceId))
                .onFailure(it -> {
                    System.out.println("Failed to register Authors service from consul: " + it.getMessage());
                });
    }
}
