package com.kit.erp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kit.erp.entity.Film;
import com.kit.erp.entity.Response;
import com.kit.erp.repo.FilmRepo;

@Service
public class FilmService {
	@Autowired
	private FilmRepo filmRepo;
	
	public Response allFilms(int pageNumber, String key,String sortBy){
		Response response=new Response();
		Sort by = Sort.by(Sort.Direction.fromString(key),sortBy);
		PageRequest of = PageRequest.of(pageNumber, 10,by);
		
		Page<Film> content = filmRepo.findAll(of);
		response.setDetails(content.getContent());
		response.setLastPage(content.isLast());
		response.setPageNumber(content.getNumber());
		response.setTotalContentOfThePage(content.getNumberOfElements());
		return response;
	}
	
	public Response searchFilm(int pageNumber,String key){
		Response response=new Response();
		
		PageRequest of = PageRequest.of(pageNumber, 10);
		Page<Film> content = filmRepo.search(of,key);
		response.setDetails(content.getContent());
		response.setLastPage(content.isLast());
		response.setPageNumber(content.getNumber());
		response.setTotalContentOfThePage(content.getNumberOfElements());
		return response;
	}
}
