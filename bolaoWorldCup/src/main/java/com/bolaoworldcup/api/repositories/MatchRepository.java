package com.bolaoworldcup.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.bolaoworldcup.api.entities.Match;

public interface MatchRepository extends JpaRepository<Match, Long>, QuerydslPredicateExecutor<Match> {
}
