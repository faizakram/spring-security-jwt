package com.spring.service;

import java.util.List;

import com.spring.dto.UserRequest;

public interface UserService {

	Long addUser(UserRequest userRequest);

	Long updateUser(UserRequest userRequest);

	List<UserRequest> getUsers();

	UserRequest getUser(Long id);

	Long delete(Long id);

}
