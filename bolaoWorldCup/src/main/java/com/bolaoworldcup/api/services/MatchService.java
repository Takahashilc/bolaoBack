package com.bolaoworldcup.api.services;

import com.bolaoworldcup.api.dto.match.MatchFinishDTO;
import com.bolaoworldcup.api.dto.match.MatchInputDTO;
import com.bolaoworldcup.api.dto.match.MatchOutputDTO;
import com.bolaoworldcup.api.entities.Group;
import com.bolaoworldcup.api.entities.Match;
import com.bolaoworldcup.api.entities.TeamMatch;
import com.bolaoworldcup.api.entities.enums.Result;
import com.bolaoworldcup.api.repositories.GroupRepository;
import com.bolaoworldcup.api.repositories.MatchRepository;
import com.bolaoworldcup.api.repositories.TeamRepository;
import com.bolaoworldcup.api.repositories.TipRepository;
import com.bolaoworldcup.api.services.exceptions.DatabaseException;
import com.bolaoworldcup.api.services.exceptions.ResourceNotFoundException;
import com.querydsl.core.BooleanBuilder;

import live.bolaocopadomundo.api.entities.QMatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TipRepository tipRepository;

    @Transactional(readOnly = true)
    public List<MatchOutputDTO> findAll(LocalDate date) {
        BooleanBuilder builder = new BooleanBuilder();
        if (date != null) {
            builder.and(com.bolaoworldcup.api.entities.QMatch.match.date.between(date.atStartOfDay(), date.atTime(23, 59, 59)));
        }

        List<Match> list = (List<Match>) matchRepository.findAll(builder);
        return list.stream().map(MatchOutputDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MatchOutputDTO findById(Long id) {
        Optional<Match> match = matchRepository.findById(id);
        Match entity = match.orElseThrow(() -> new ResourceNotFoundException("Match not found"));
        return new MatchOutputDTO(entity);
    }

    @Transactional
    public MatchOutputDTO insert(MatchInputDTO dto) {
        Match entity = new Match();
        copyDtoToEntity(dto, entity);
        entity = matchRepository.save(entity);
        return new MatchOutputDTO(entity);
    }

    @Transactional
    public MatchOutputDTO update(Long id, MatchInputDTO dto) {
        try {
            Match entity = matchRepository.getOne(id);
            copyDtoToEntity(dto, entity);
            entity = matchRepository.save(entity);
            return new MatchOutputDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    @Transactional
    public MatchOutputDTO finishMatch(Long id, MatchFinishDTO dto) {
        try {
            Match entity = matchRepository.getOne(id);

            if (!(entity.getDate().isBefore(LocalDateTime.now()))) {
                throw new DatabaseException("Match hasn't happened yet");
            }

            if (entity.getResult() != Result.NOT_PLAYED) {
                throw new DatabaseException("Match already finished");
            }

            entity.setResult(dto.getResult());
            entity = matchRepository.save(entity);

            tipRepository.findAllByMatchIdAndResultEquals(id, dto.getResult()).forEach(tip ->
                    tip.getUser().setPoints(tip.getUser().getPoints() + 1)
            );

            return new MatchOutputDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    public void delete(Long id) {
        try {
            matchRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(MatchInputDTO dto, Match entity) {
        List<Group> groups = new ArrayList<>();

        entity.setDate(dto.getDate());
        entity.getTeams().clear();

        if (dto.getTeams().size() != 2) {
            throw new DatabaseException("Match must have 2 teams");
        }

        dto.getTeams().forEach(teamMatchDTO ->
                entity.getTeams().add(
                        new TeamMatch(
                                teamRepository.findByName(teamMatchDTO.getTeam())
                                        .orElseThrow(() -> new ResourceNotFoundException("Team not found")),
                                entity,
                                teamMatchDTO.getType())
                )
        );

        if (!isTeamGroupMatch(entity.getTeams())) {
            throw new DatabaseException("Teams must be in the same group");
        }
    }

    private boolean isTeamGroupMatch(Set<TeamMatch> teams) {
        String group = teams.stream().findFirst().get().getTeam().getGroup().getName();
        return teams.stream().allMatch(teamMatch -> teamMatch.getTeam().getGroup().getName().equals(group));
    }
}
