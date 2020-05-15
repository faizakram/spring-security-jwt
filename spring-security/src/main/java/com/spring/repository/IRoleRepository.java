package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.model.Role;


public interface IRoleRepository extends JpaRepository<Role, Long>{

}
