package com.programacion.distribuida;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Libro {
    private String isbn;
    private String title;
}
