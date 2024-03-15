package com.kit.erp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kit.erp.entity.Response;
import com.kit.erp.entity.TeamDetails;
import com.kit.erp.entity.TeamDetailsDto;
import com.kit.erp.repo.TeamRepo;

@Service
public class TeamDetailsService {
	@Autowired
	private TeamRepo teamRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	public Response findAll(int pageNumber,int pageSize){
		Response response= new Response();
		
		Pageable page=PageRequest.of(pageNumber, pageSize);
		Page<TeamDetails> all = teamRepo.findAll(page);
		List<TeamDetails> content = all.getContent();
		List<TeamDetailsDto> collect = content.stream().map(li->this.modelMapper.map(li, TeamDetailsDto.class)).collect(Collectors.toList());
		//response.setDetails(collect);
		response.setLastPage(all.isLast());
		response.setPageNumber(all.getNumber());
		response.setTotalContentOfThePage(all.getNumberOfElements());
		
		return response;
	}
}
