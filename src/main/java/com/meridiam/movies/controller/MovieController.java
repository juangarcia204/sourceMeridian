package com.meridiam.movies.controller;

import com.meridiam.movies.model.MovieDTO;
import com.meridiam.movies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<MovieDTO> saveMovie(@RequestBody MovieDTO movie){
        MovieDTO movieDto = movieService.saveMovie(movie);
        return new ResponseEntity<>(movieDto, HttpStatus.CREATED);
    }

    @GetMapping
    public List<MovieDTO> getAllMovies(){
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable long id){
        return  movieService.getMovieById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable long id, @RequestBody MovieDTO movie){
        return movieService.getMovieById(id)
                .map(mov ->{
                    mov.setDescription(movie.getDescription());
                    mov.setName(movie.getName());
                    mov.setGenre(movie.getGenre());
                    mov.setYearMovie(movie.getYearMovie());

                   return new ResponseEntity<>(movieService.updateMovie(mov), HttpStatus.OK) ;
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable long id){
        movieService.deleteMovie(id);
        return new ResponseEntity<>("Movie has been eliminated", HttpStatus.OK);

    }


}
