package com.bolaoworldcup.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolaoworldcup.api.dto.tip.TipInputDTO;
import com.bolaoworldcup.api.dto.tip.TipOutputDTO;
import com.bolaoworldcup.api.entities.Match;
import com.bolaoworldcup.api.entities.Tip;
import com.bolaoworldcup.api.entities.User;
import com.bolaoworldcup.api.repositories.MatchRepository;
import com.bolaoworldcup.api.repositories.TipRepository;
import com.bolaoworldcup.api.repositories.UserRepository;
import com.bolaoworldcup.api.services.exceptions.DatabaseException;
import com.bolaoworldcup.api.services.exceptions.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TipService {

    @Autowired
    private TipRepository tipRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Transactional(readOnly = true)
    public List<TipOutputDTO> findAllPaged() {
        List<Tip> list = tipRepository.findAll();
        return list.stream().map(TipOutputDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TipOutputDTO findById(Long id) {
        Optional<Tip> tip = tipRepository.findById(id);
        Tip entity = tip.orElseThrow(() -> new ResourceNotFoundException("Tip not found"));
        return new TipOutputDTO(entity);
    }

    @Transactional
    public TipOutputDTO insert(TipInputDTO dto) {
        Tip entity = new Tip();
        copyDtoToEntity(dto, entity);
        entity = tipRepository.save(entity);
        return new TipOutputDTO(entity);
    }

    public void delete(Long id) {
        try {
            tipRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(TipInputDTO dto, Tip entity) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Match match = matchRepository.getOne(dto.getMatch());

        if (match.getDate().isBefore(LocalDateTime.now())) {
            throw new DatabaseException("Match already started");
        }

        Optional<Tip> tip = tipRepository.findByMatchAndUser(match, user);
        if (tip.isPresent()) {
            throw new DatabaseException("User already tipped this match");
        }

        entity.setMatch(match);
        entity.setResult(dto.getResult());
        entity.setUser(user);
    }
}
