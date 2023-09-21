package com.meridiam.movies.repository;

import com.meridiam.movies.entities.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class MovieRepositoryTest {

    private List<Movie> listMovie = null;
    private Movie movie = null;

    @Autowired
    private Movierepository movierepository;

    @BeforeEach
    void init(){
        listMovie = List.of(new Movie(null, "History","movie about some people", new Date(), "Action"),
                new Movie(null, "comedy Movie","movie about comedy", new Date(), "Comedy"),
                new Movie(null, "terror Movie","movie about terror", new Date(), "Terror"));
        movie = new Movie(null, "History","movie about some people", new Date(), "Action");
    }

    @Test
    void saveMovieSuccessfully(){

        Movie savedMovie = movierepository.save(movie);

        assertThat(savedMovie).isNotNull();
        assertThat(savedMovie.getIdMovie()).isGreaterThan(0);
        assertThat(savedMovie.getName()).isEqualTo(movie.getName());

    }

    @Test
    void getAllMovieSuccessfully(){

        movierepository.saveAll(listMovie);

        List<Movie> listMovies = movierepository.findAll();

        assertThat(listMovies).isNotNull();
        assertThat(listMovies.size()).isEqualTo(3);

    }

    @Test
    void getAllMovieEmpty(){
        List<Movie> listMovies = movierepository.findAll();

        assertThat(listMovies).isEmpty();
        assertThat(listMovies.size()).isEqualTo(0);

    }

    @Test
    void getMovieByIdSuccessfully(){

        movierepository.save(movie);
        Movie foundMovie = movierepository.findById(movie.getIdMovie()).get();

        assertThat(foundMovie).isNotNull();
        assertThat(foundMovie.getIdMovie()).isEqualTo(movie.getIdMovie());
        assertThat(foundMovie.getName()).isEqualTo(movie.getName());
    }

    @Test
    void updateMovieSuccessfully(){

        movierepository.save(movie);

        Movie foundMovie = movierepository.findById(movie.getIdMovie()).get();
        foundMovie.setName("modifyName");

        Movie updateMovie = movierepository.save(movie);

        assertThat(updateMovie).isNotNull();
        assertThat(updateMovie.getIdMovie()).isEqualTo(foundMovie.getIdMovie());
        assertThat(updateMovie.getName()).isEqualTo("modifyName");

    }

    @Test
    void deleteMovieSuccessfully(){

        movierepository.save(movie);

        movierepository.deleteById(movie.getIdMovie());

        Optional<Movie> movieOptional = movierepository.findById(movie.getIdMovie());
        assertThat(movieOptional).isEmpty();

    }
}
