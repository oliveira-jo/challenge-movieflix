package com.devsuperior.movieflix.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {

	@Autowired
	private MovieRepository repository;

	@Autowired
	private ReviewService reviewService;

	@Transactional(readOnly = true)
	public MovieDetailsDTO findById(Long id) {

		var resource = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Resource not found"));
		return new MovieDetailsDTO(resource);

	}

	@Transactional(readOnly = true)
	public Page<MovieCardDTO> findByGenre(String genreId, Pageable pageable) {

		if (genreId.equals("0"))
			return repository.findAll(pageable).map(obj -> new MovieCardDTO(obj));

		else
			return repository.findByGenreId(Long.parseLong(genreId), pageable)
					.map(obj -> new MovieCardDTO(obj));

	}

	@Transactional(readOnly = true)
	public List<ReviewDTO> findMoviesReviews(Long id) {
		return reviewService.findAllByMovieId(id);
	}

}
