package com.bolaoworldcup.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.bolaoworldcup.api.entities.Log;
import com.bolaoworldcup.api.entities.Match;

public interface LogRepository extends JpaRepository<Log, Long>, QuerydslPredicateExecutor<Log> {
}
