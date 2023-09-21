package com.meridiam.movies.service.impl;

import com.meridiam.movies.entities.Movie;
import com.meridiam.movies.exception.ResourceNotFoundException;
import com.meridiam.movies.model.MovieDTO;
import com.meridiam.movies.repository.Movierepository;
import com.meridiam.movies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {


    private final Movierepository movierepository;

    @Autowired
    public MovieServiceImpl(Movierepository movierepository) {
        this.movierepository = movierepository;
    }

    @Override
    public MovieDTO saveMovie(MovieDTO movieDto){
        Optional<Movie> existMovie = movierepository.findByName(movieDto.getName());
        if (existMovie.isPresent()){
            throw new ResourceNotFoundException("Movie already exists!!");
        }
        Movie savedMovie = movierepository.save(this.setInfoMovie(movieDto));
        movieDto.setIdMovie(savedMovie.getIdMovie());
        return movieDto;
    }

    @Override
    public Optional<MovieDTO>  getMovieById(long id){
        Optional<Movie> movie =  movierepository.findById(id);
        return  movie.map(this::setInfoMovieDTO);
    }

    @Override
    public List<MovieDTO> getAllMovies(){
        List<Movie> listMovies = movierepository.findAll();
        return listMovies.stream().map(this::setInfoMovieDTO).collect(Collectors.toList());
    }

    @Override
    public MovieDTO updateMovie(MovieDTO movie){
        return setInfoMovieDTO(movierepository.save(setInfoMovie(movie))) ;
    }

    @Override
    public void deleteMovie(Long idMovie){
        movierepository.deleteById(idMovie);
    }

    private Movie setInfoMovie(MovieDTO movieDto){
        return new Movie(movieDto.getIdMovie(), movieDto.getName(), movieDto.getDescription(), movieDto.getYearMovie(), movieDto.getGenre());
    }

    private MovieDTO setInfoMovieDTO(Movie movie){
        return new MovieDTO(movie.getIdMovie(), movie.getName(), movie.getDescription(), movie.getYearMovie(), movie.getGenre());
    }


}
