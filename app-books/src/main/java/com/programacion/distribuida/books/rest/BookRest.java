package com.programacion.distribuida.books.rest;

import com.programacion.distribuida.books.db.Book;
import com.programacion.distribuida.books.dto.BookDto;
import com.programacion.distribuida.books.repo.BookRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
@Transactional
public class BookRest {

    final BookRepository bookRepository;

    @GET
    public List<BookDto> findAll() {
        return bookRepository.streamAll()
                .map(it -> BookDto.builder()
                        .isbn(it.getIsbn())
                        .title(it.getTitle())
                        .price(it.getPrice())
//                        .inventorySold(it.getInventory().getSold())
//                        .inventorySupplies(it.getInventory().getSupplied())
                        .build())
                .toList();
    }

    @GET
    @Path("/{isbn}")
    public Response findByIsbn(@PathParam("isbn") String isbn) {
        return bookRepository.findByIdOptional(isbn)
                .map(it -> BookDto.builder()
                        .isbn(it.getIsbn())
                        .title(it.getTitle())
                        .price(it.getPrice())
//                        .inventorySold(it.getInventory().getSold())
//                        .inventorySupplies(it.getInventory().getSupplied())
                        .build())
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @PUT
    @Path("/{isbn}")
    public Response updateBook(@PathParam("isbn") String isbn, Book book) {
        bookRepository.update(isbn, book);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{isbn}")
    public Response deleteBook(@PathParam("isbn") String isbn) {
        bookRepository.deleteById(isbn);
        return Response.ok().build();
    }

    @POST
    public Response insertBook(Book book) {
        bookRepository.persist(book);
        UriBuilder.fromUri("/books/{isbn}")
                .build();
        return Response.ok().build();
    }
}
