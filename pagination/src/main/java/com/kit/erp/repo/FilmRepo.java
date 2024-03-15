package com.kit.erp.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kit.erp.entity.Film;
@Repository
public interface FilmRepo extends JpaRepository<Film,Integer>{
	@Query(value = "SELECT u FROM Film u WHERE LOWER(u.title) LIKE LOWER(CONCAT('%',:search,'%'))")
	public Page<Film> search(PageRequest req, String search);
}
