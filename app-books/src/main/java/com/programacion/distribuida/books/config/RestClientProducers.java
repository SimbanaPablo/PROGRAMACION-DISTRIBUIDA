//package com.programacion.distribuida.books.config;
//
//import com.programacion.distribuida.books.clients.AuthorRestClient;
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//import org.eclipse.microprofile.config.inject.ConfigProperty;
//import org.eclipse.microprofile.rest.client.RestClientBuilder;
//
//import java.net.URI;
//
//@ApplicationScoped
//public class RestClientProducers {
//    @Inject
//    @ConfigProperty(name = "authors.url")
//    String url;
//
//    public AuthorRestClient authorRestClient() {
//        return RestClientBuilder.newBuilder()
//                .baseUri(url)
//                .build(AuthorRestClient.class);
//    }
//}
