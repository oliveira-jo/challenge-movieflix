package com.devsuperior.movieflix.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.dto.UserDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.repositories.UserRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private UserService service;

	@Transactional
	public ReviewDTO save(ReviewDTO dto) {

		UserDTO authenticated = service.getProfile();
		User loggedUser = userRepository.getReferenceById(authenticated.getId());

		Movie movie = movieRepository.findById(dto.getMovieId()).orElseThrow(
				() -> new ResourceNotFoundException("Resource not found"));

		Review review = new Review(null, dto.getText(), movie, loggedUser);

		return new ReviewDTO(reviewRepository.save(review));

	}

	@Transactional(readOnly = true)
	public List<ReviewDTO> findAllByMovieId(Long id) {
		List<Review> reviews = reviewRepository.findAllByMovieId(id);
		return reviews.stream().map(obj -> new ReviewDTO(obj)).toList();
	}

}
