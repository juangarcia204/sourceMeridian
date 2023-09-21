package com.meridiam.movies.service.impl;

import com.meridiam.movies.entities.Movie;
import com.meridiam.movies.exception.ResourceNotFoundException;
import com.meridiam.movies.model.MovieDTO;
import com.meridiam.movies.repository.Movierepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class MovieServiceImplTest {

    private List<Movie> listMovie = null;
    private MovieDTO movieDTO = null;
    private Movie movie = null;

    @Mock
    private Movierepository movierepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    @BeforeEach
    void init(){
        listMovie = List.of(new Movie(null, "History","movie about some people", new Date(), "Action"),
                new Movie(null, "comedy Movie","movie about comedy", new Date(), "Comedy"),
                new Movie(null, "terror Movie","movie about terror", new Date(), "Terror"));
        movieDTO = new MovieDTO(null, "History","movie about some people", new Date(), "Action");
        movie = new Movie(null, "History","movie about some people", new Date(), "Action");
    }


    @Test
    public void saveMovieSuccessfully(){

        given(movierepository.findByName(movie.getName()))
                .willReturn(Optional.empty());
        given(movierepository.save(any())).willReturn(movie);

        MovieDTO savedMovie = movieService.saveMovie(movieDTO);

        assertThat(savedMovie).isNotNull();
        assertThat(savedMovie.getName()).isEqualTo(movieDTO.getName());
    }

    @Test
    public void saveMovieUnSuccessfully(){

        given(movierepository.findByName(movie.getName()))
                .willReturn(Optional.of(movie));

        assertThrows(ResourceNotFoundException.class, () -> movieService.saveMovie(movieDTO));

        verify(movierepository,never()).save(any(Movie.class));

    }

    @Test
    public void getAllMoviesSuccessfully(){

        given(movierepository.findAll())
                .willReturn(listMovie);

        List<MovieDTO> listMOvies = movieService.getAllMovies();

        assertThat(listMOvies).isNotNull();
        assertThat(listMOvies.size()).isEqualTo(3);

    }

    @Test
    public void getAllMoviesEmpty(){

        given(movierepository.findAll())
                .willReturn(List.of());

        List<MovieDTO> listMOvies = movieService.getAllMovies();

        assertThat(listMOvies).isEmpty();
        assertThat(listMOvies.size()).isEqualTo(0);

    }

    @Test
    public void getMoviesByIdSuccessfully(){
        movie.setIdMovie(1l);
        given(movierepository.findById(movie.getIdMovie()))
                .willReturn(Optional.of(movie));

        MovieDTO foundMovie = movieService.getMovieById(movie.getIdMovie()).get();


        assertThat(foundMovie).isNotNull();
        assertThat(foundMovie.getIdMovie()).isEqualTo(movie.getIdMovie());
        assertThat(foundMovie.getName()).isEqualTo(movie.getName());
    }

    @Test
    public void getMoviesByIdUnSuccessfully(){
        movie.setIdMovie(1l);
        given(movierepository.findById(movie.getIdMovie()))
                .willReturn(Optional.empty());

        Optional<MovieDTO> foundMovie = movieService.getMovieById(movie.getIdMovie());

        assertFalse(foundMovie.isPresent());

    }

    @Test
    public void updateMoviesSuccessfully(){
        movieDTO.setName("nameModify");
        movie.setName("nameModify");
        given(movierepository.save(any()))
                .willReturn(movie);


        MovieDTO movieModify = movieService.updateMovie(movieDTO);

        assertThat(movieModify.getName()).isEqualTo(movieDTO.getName());


    }


}
