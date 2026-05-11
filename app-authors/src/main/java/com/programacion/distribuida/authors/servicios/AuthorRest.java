package com.programacion.distribuida.authors.servicios;

import com.programacion.distribuida.authors.db.Author;
import com.programacion.distribuida.authors.repo.AuthorRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

@Path("/author")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthorRest {

    @Inject
    AuthorRepository authorRepository;

    @Inject
    @ConfigProperty(name = "quarkus.http.port")

    Integer httpPorrt;

    @GET
    public List<Author> getAll() {
        return authorRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Integer id) {

        return authorRepository.findByIdOptional(id)
                .map(it -> {
                    it.setNombre(it.getNombre() + " - " + httpPorrt);
                    return it;
                })
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND)
                )
                .build();
    }
}
