package com.meridiam.movies.service;

import com.meridiam.movies.model.MovieDTO;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    MovieDTO saveMovie(MovieDTO movieDto);

    Optional<MovieDTO> getMovieById(long id);

    List<MovieDTO> getAllMovies();

    MovieDTO updateMovie(MovieDTO movie);

    void deleteMovie(Long idMovie);
}
