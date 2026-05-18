package com.programacion.distribuida.books.rest;

import com.programacion.distribuida.books.clients.AuthorRestClient;
import com.programacion.distribuida.books.db.Book;
import com.programacion.distribuida.books.dto.BookDto;
import com.programacion.distribuida.books.repo.BookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
@ApplicationScoped
public class BookRest {

//    @Inject
//    BookRepository bookRepository;
//    @Inject
//    @RestClient
//    AuthorRestClient client;
    final BookRepository bookRepository;
    final AuthorRestClient client;

    @Inject
    public BookRest(BookRepository bookRepository, @RestClient  AuthorRestClient client) {
        this.bookRepository = bookRepository;
        this.client = client;
    }

    @GET
    public List<BookDto> findAll() {
        return bookRepository.streamAll()
                .map(book ->{
                    // consultar authores en 127.0.0.1:8070
                    var authors = client.findByBook(book.getIsbn());
                    return BookDto.builder()
                            .isbn(book.getIsbn())
                            .title(book.getTitle())
                            .price(book.getPrice())
                            .authors(authors)
                            .inventorySold(book.getInventory() != null ? book.getInventory().getSold() : null)
                            .inventorySupplies(book.getInventory() != null ? book.getInventory().getSupplied() : null)
                            .build();
                })
                .toList();
    }

    @GET
    @Path("/{isbn}")
    public Response findByIsbn(@PathParam("isbn") String isbn) {

        return bookRepository.findByIdOptional(isbn)
                .map(book -> {
                    // consultar authores en 127.0.0.1:8070
                    var authors = client.findByBook(isbn);
                    return BookDto.builder()
                            .isbn(book.getIsbn())
                            .title(book.getTitle())
                            .price(book.getPrice())
                            .authors(authors)
                            .inventorySold(book.getInventory() != null ? book.getInventory().getSold() : null)
                            .inventorySupplies(book.getInventory() != null ? book.getInventory().getSupplied() : null)
                            .build();
                })
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
