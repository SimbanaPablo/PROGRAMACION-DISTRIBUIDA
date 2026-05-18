package com.programacion.distribuida.authors.rest;

import com.programacion.distribuida.authors.db.Author;
import com.programacion.distribuida.authors.dto.AuthorDto;
import com.programacion.distribuida.authors.repo.AuthorRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

@Path("/authors")
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

    @GET
    @Path("/find/{isbn}")
    public List<AuthorDto> findByBook(@PathParam("isbn") String isbn) {
        return authorRepository.findByBook(isbn)
                .stream()
                .peek(it -> {
                    it.setNombre(it.getNombre() + " - " + httpPorrt);
                })
                .map(it -> AuthorDto.builder()
                        .id(it.getId())
                        .name(it.getNombre())
                        .build()
                )
                .toList();
    }
}
