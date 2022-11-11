package com.bolaoworldcup.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bolaoworldcup.api.entities.Group;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByName(String name);
}
