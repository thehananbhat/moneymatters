package dev.bank.moneymatters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.bank.moneymatters.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {
	Role findById(int id);
}
