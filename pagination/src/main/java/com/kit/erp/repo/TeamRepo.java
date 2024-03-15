package com.kit.erp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kit.erp.entity.TeamDetails;

public interface TeamRepo extends JpaRepository<TeamDetails,Long>{
	
}
