package com.meridiam.movies.repository;

import com.meridiam.movies.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface Movierepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findByName(String name);
}
