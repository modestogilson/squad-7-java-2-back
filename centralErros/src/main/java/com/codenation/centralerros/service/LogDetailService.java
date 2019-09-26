package com.codenation.centralerros.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codenation.centralerros.dto.LogDetailDTO;
import com.codenation.centralerros.exception.InvalidLogDetailException;
import com.codenation.centralerros.model.Environment;
import com.codenation.centralerros.repository.LogDetailRepository;

@Service
public class LogDetailService {

	@Autowired
	private LogDetailRepository logDetailRepository;

	public void save(LogDetailDTO logDetailDTO) {
		validateLog( logDetailDTO );
		logDetailRepository.save( logDetailDTO.toLogDetail() );
	}

	public List<LogDetailDTO> findAll() {
		return logDetailRepository.findAll().stream()
				.map( LogDetailDTO::new )
				.collect( Collectors.toList() );
	}

	public LogDetailDTO findById(Long id) {
		return logDetailRepository.findById( id )
				.map( LogDetailDTO::new )
				.orElse( null );
	}

	public List<LogDetailDTO> findByOriginEnvironment(Environment environment) {
		return logDetailRepository.findByOriginEnvironment( environment ).stream()
				.map( LogDetailDTO::new )
				.collect( Collectors.toList() );
	}

	public void validateLog(LogDetailDTO logDetailDTO) {
		if (logDetailDTO.getDetail().isEmpty() || logDetailDTO.getDetail() == null ||
				logDetailDTO.getEnvironment().isEmpty() || logDetailDTO.getEnvironment() == null ||
				logDetailDTO.getLevel().isEmpty() || logDetailDTO.getLevel() == null ||
				logDetailDTO.getTimeEvent() == null ||
				logDetailDTO.getTitle().isEmpty() || logDetailDTO.getTitle() == null) {
			throw new InvalidLogDetailException();
		}
	}
}
