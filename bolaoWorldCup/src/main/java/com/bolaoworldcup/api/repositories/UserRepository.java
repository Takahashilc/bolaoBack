package com.bolaoworldcup.api.repositories;

import com.bolaoworldcup.api.entities.Match;
import com.bolaoworldcup.api.entities.User;
import com.querydsl.core.BooleanBuilder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {
    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
    Boolean existsByNickname(String nickname);
    Boolean existsByEmail(String email);
}