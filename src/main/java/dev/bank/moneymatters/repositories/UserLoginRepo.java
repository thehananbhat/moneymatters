package dev.bank.moneymatters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.bank.moneymatters.entities.User;


@Repository
public interface UserLoginRepo extends JpaRepository<User, String> {
	User findUserByUserIdAndPassword(String userId, String password);
	User findUserByUserIdAndRoleRoleId(String userId, int roleId);
	User findUserByUserId(String userId);
}
