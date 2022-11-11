package com.bolaoworldcup.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bolaoworldcup.api.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByAuthority(String role);
}
