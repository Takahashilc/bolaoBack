package com.bolaoworldcup.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bolaoworldcup.api.entities.Team;
import com.bolaoworldcup.api.entities.TeamMatch;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByName(String team);
}
