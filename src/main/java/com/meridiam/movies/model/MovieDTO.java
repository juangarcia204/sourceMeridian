package com.meridiam.movies.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class MovieDTO {

    private Long idMovie;

    private String name;
    private String description;
    private Date yearMovie;
    private String genre;
}
