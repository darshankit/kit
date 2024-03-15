package com.kit.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kit.erp.entity.Response;
import com.kit.erp.entity.TeamDetails;
import com.kit.erp.service.TeamDetailsService;

import jakarta.websocket.server.PathParam;

@RestController
public class TeamController {
	@Autowired
	private TeamDetailsService teamService;
	
	@GetMapping("details")
	public ResponseEntity<Response>all(@RequestParam(defaultValue = "1",required = false) int pageNumber,
										@RequestParam(defaultValue = "5", required=false)int pageSize){
		Response findAll = teamService.findAll(pageNumber,pageSize);
		return ResponseEntity.ok(findAll);
	}
}
