package com.programacion.distribuida.books;

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
import java.net.UnknownHostException;

@ApplicationScoped
public class BooksLifeCicle {

    @Inject
    @ConfigProperty(name = "consul.host", defaultValue = "127.0.0.1")
    String consultHost;

    @Inject
    @ConfigProperty(name = "consul.port", defaultValue = "8500")
    Integer consultPort;

    @Inject
    @ConfigProperty(name = "quarkus.http.port", defaultValue = "8080")
    Integer appPort;

    String serviceId;

    public void init(@Observes StartupEvent event, Vertx vertex) {
        System.out.println("books-lifecycle: init");
        try {
            ConsulClientOptions option = new ConsulClientOptions()
                    .setHost(consultHost)
                    .setPort(consultPort);

            ConsulClient client = ConsulClient.create(vertex, option);

            String ipAddress = InetAddress.getLocalHost().getHostAddress();
            serviceId = "app-books-%s:%d".formatted(ipAddress, appPort);

            var urlCheck = "http://%s:%d/ping".formatted(ipAddress, appPort);
            CheckOptions checkOptions = new CheckOptions()
                    .setHttp(urlCheck)
                    .setInterval("10s")
                    .setDeregisterAfter("10s");

            ServiceOptions serviceOptions = new ServiceOptions()
                    .setName("app-books")
                    .setId(serviceId)
                    .setAddress(ipAddress)
                    .setPort(appPort)
                    .setCheckOptions(checkOptions);

            client.registerService(serviceOptions)
                    .onSuccess(it -> System.out.println("books-lifecycle: success Books Service registred in consult with ID:  " + serviceId))
                    .onFailure(it -> {
                        System.out.println("Failed to register Books Service in Consul :  " + it.getMessage());
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy(@Observes ShutdownEvent event, Vertx vertex) {
        System.out.println("books-lifecycle: destroy");
        ConsulClientOptions option = new ConsulClientOptions()
                .setHost(consultHost)
                .setPort(consultPort);
        ConsulClient client = ConsulClient.create(vertex, option);

        client.deregisterService(serviceId)
                .onSuccess(it -> System.out.println("Books service deregistered from consul ID: " + serviceId))
                .onFailure(it -> {
                    System.out.println("Failed to deregister Books from Consul: " + it.getMessage());
                });
    }
}
