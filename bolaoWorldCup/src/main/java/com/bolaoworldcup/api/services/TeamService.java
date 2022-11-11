package com.bolaoworldcup.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolaoworldcup.api.dto.team.TeamInputDTO;
import com.bolaoworldcup.api.dto.team.TeamOutputDTO;
import com.bolaoworldcup.api.entities.Group;
import com.bolaoworldcup.api.entities.Team;
import com.bolaoworldcup.api.repositories.GroupRepository;
import com.bolaoworldcup.api.repositories.TeamRepository;
import com.bolaoworldcup.api.services.exceptions.DatabaseException;
import com.bolaoworldcup.api.services.exceptions.ResourceNotFoundException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Transactional(readOnly = true)
    public List<TeamOutputDTO> findAllPaged() {
        List<Team> list = teamRepository.findAll();
        return list.stream().map(TeamOutputDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TeamOutputDTO findById(Long id) {
        Optional<Team> team = teamRepository.findById(id);
        Team entity = team.orElseThrow(() -> new ResourceNotFoundException("Team not found"));
        return new TeamOutputDTO(entity);
    }

    @Transactional
    public TeamOutputDTO insert(TeamInputDTO dto) {
        Team entity = new Team();
        copyDtoToEntity(dto, entity);
        entity = teamRepository.save(entity);
        return new TeamOutputDTO(entity);
    }

    @Transactional
    public TeamOutputDTO update(Long id, TeamInputDTO dto) {
        try {
            Team entity = teamRepository.getOne(id);
            copyDtoToEntity(dto, entity);
            entity = teamRepository.save(entity);
            return new TeamOutputDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    public void delete(Long id) {
        try {
            teamRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(TeamInputDTO dto, Team entity) {
        Group group = groupRepository.findByName(dto.getGroup()).orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        entity.setName(dto.getName());
        entity.setAcronym(dto.getAcronym().toUpperCase());
        entity.setFlagUrl(dto.getFlagUrl());

        if (group.getTeams().size() < 4) {
            entity.setGroup(group);
        } else {
            throw new DatabaseException("Group already has 4 teams");
        }
    }
}
