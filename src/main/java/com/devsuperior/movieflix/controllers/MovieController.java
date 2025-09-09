package com.devsuperior.movieflix.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.services.MovieService;

@RestController
@RequestMapping("/movies")
public class MovieController {

	@Autowired
	private MovieService service;

	@PreAuthorize("hasAnyRole('VISITOR', 'MEMBER')")
	@GetMapping("/{id}")
	public ResponseEntity<MovieDetailsDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(service.findById(id));
	}

	@PreAuthorize("hasAnyRole('VISITOR', 'MEMBER')")
	@GetMapping
	public ResponseEntity<Page<MovieCardDTO>> findAll(
			@RequestParam(value = "genreId", defaultValue = "0") String genreId,
			@RequestParam(value = "sort", defaultValue = "title") String sort,
			Pageable pageable) {
		Pageable pg = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sort));
		return ResponseEntity.ok().body(service.findByGenre(genreId, pg));
	}

	@PreAuthorize("hasAnyRole('VISITOR', 'MEMBER')")
	@GetMapping("/{id}/reviews")
	public ResponseEntity<List<ReviewDTO>> findMoviesReviews(@PathVariable Long id) {
		return ResponseEntity.ok().body(service.findMoviesReviews(id));
	}
}
