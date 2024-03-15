package com.kit.erp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kit.erp.entity.Film;
import com.kit.erp.entity.Response;
import com.kit.erp.service.FilmService;

@RestController
public class FilmController {
	@Autowired
	private FilmService filmService;

	@GetMapping("allFilms")
	public ResponseEntity<Response> getAllFilms(@RequestParam(defaultValue = "0", required = false) int pageNumber,
			@RequestParam(defaultValue = "title", required = false) String sortBy,
			@RequestParam(defaultValue="asc",required = false)String direction) {
		Response all = filmService.allFilms(pageNumber,direction, sortBy);
		return ResponseEntity.ok(all);
	}

	@GetMapping("search")
	public ResponseEntity<Response> searchFilm(@RequestParam(defaultValue = "0", required = false) int pageNumber,
			@RequestParam String search) {
		Response all = filmService.searchFilm(pageNumber,search);
		return ResponseEntity.ok(all);
	}
}
