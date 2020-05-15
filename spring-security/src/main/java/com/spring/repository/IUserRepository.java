
package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.model.User;

public interface IUserRepository extends JpaRepository<User, Long> {

	public User findByUsername(String username);
}
