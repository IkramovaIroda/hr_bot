package com.example.hr_bot.repository;


import com.example.hr_bot.entity.Role;
import com.example.hr_bot.entity.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(RoleEnum name);
}
