package com.bolaoworldcup.api.services;

import com.bolaoworldcup.api.dto.log.LogInputDTO;
import com.bolaoworldcup.api.dto.log.LogOutputDTO;
import com.bolaoworldcup.api.dto.match.MatchOutputDTO;
import com.bolaoworldcup.api.entities.Log;
import com.bolaoworldcup.api.entities.Match;
import com.bolaoworldcup.api.entities.enums.Action;
import com.bolaoworldcup.api.repositories.LogRepository;
import com.bolaoworldcup.api.repositories.UserRepository;
import com.bolaoworldcup.api.services.exceptions.ResourceNotFoundException;
import com.querydsl.core.BooleanBuilder;

import live.bolaocopadomundo.api.entities.QLog;
import live.bolaocopadomundo.api.entities.QMatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<LogOutputDTO> findAllPaged(LocalDate date) {
        BooleanBuilder builder = new BooleanBuilder();

        if (date != null) {
            builder.and(com.bolaoworldcup.api.entities.QLog.log.date.between(date.atStartOfDay(), date.atTime(23, 59, 59)));
        }

        List<Log> list = (List<Log>) logRepository.findAll(builder);
        return list.stream().map(LogOutputDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LogOutputDTO findById(Long id) {
        Optional<Log> group = logRepository.findById(id);
        Log entity = group.orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        return new LogOutputDTO(entity);
    }

    @Transactional
    public LogOutputDTO insert(LogInputDTO dto) {
        Log entity = new Log();
        entity.setUser(userRepository.getOne(dto.getUser()));

        try {
            entity.setAction(dto.getAction());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid action");
        }

        entity = logRepository.save(entity);
        return new LogOutputDTO(entity);
    }
}
