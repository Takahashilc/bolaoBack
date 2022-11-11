package com.bolaoworldcup.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bolaoworldcup.api.entities.Match;
import com.bolaoworldcup.api.entities.Tip;
import com.bolaoworldcup.api.entities.User;
import com.bolaoworldcup.api.entities.enums.Result;

import java.util.List;
import java.util.Optional;

public interface TipRepository extends JpaRepository<Tip, Long> {
    List<Tip> findAllByMatchIdAndResultEquals(Long matchId, Result result);

    Optional<Tip> findByMatchAndUser(Match match, User user);
}
