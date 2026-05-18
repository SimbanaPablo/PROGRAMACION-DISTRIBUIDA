package com.programacion.distribuida.books.servicios;

import com.programacion.distribuida.books.clients.AuthorRestClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

public class RestClientProducers {
////    @Inject
////    @ConfigProperty(name = "authors.url")
////    String url;
//
//    @ApplicationScoped
//    @Produces
//    public AuthorRestClient authorRestClient() {
//        return RestClientBuilder.newBuilder()
//                .baseUri(url)
//                .build(AuthorRestClient.class);
//
//    }
}
