package com.spring.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.dto.UserRequest;
import com.spring.model.Role;
import com.spring.model.User;
import com.spring.repository.IUserRepository;
import com.spring.util.CommonConstant;
import com.spring.web.exception.ErrorCodeHelper;
import com.spring.web.exception.ErrorInfo;
import com.spring.web.exception.ServiceException;

@Service
@Transactional
public class AuthUserDetailsService implements UserService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private ErrorCodeHelper errorCodeHelper;

	@Override
	public Long addUser(UserRequest userRequest) {
		User user = new User();
		user.setUsername(userRequest.getUsername());// Once you set the user name you can not update it
		user.setRoles(new HashSet<>());
		return addOrUpdateUser(user, userRequest);
	}

	private Long addOrUpdateUser(User user, UserRequest userRequest) {
		user.setCredential(bCryptPasswordEncoder.encode(userRequest.getCredential()));
		user.setEnabled(true);
		Set<Role> roles = user.getRoles();
		roles.clear();
		userRequest.getRoleIds().forEach(e -> {
			Role role = new Role();
			role.setId(e);
			roles.add(role);
		});
		user.setRoles(roles);
		return userRepository.save(user).getId();
	}

	@Override
	public Long updateUser(UserRequest userRequest) {
		return addOrUpdateUser(getUserById(userRequest.getId()), userRequest);
	}

	private User getUserById(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			ErrorInfo errorInfo = errorCodeHelper.getErrorInfo(CommonConstant.E1004_ERROR_CODE,
					CommonConstant.E1004_ERROR_DESCRIPTION);
			throw new ServiceException(errorInfo, HttpStatus.NOT_FOUND);
		}
		return user.get();
	}

	@Override
	public List<UserRequest> getUsers() {
		List<User> users = userRepository.findAll();
		return users.stream().map(this::getUserRequest).collect(Collectors.toList());
	}

	@Override
	public UserRequest getUser(Long id) {
		return getUserRequest(getUserById(id));
	}

	private UserRequest getUserRequest(User user) {
		UserRequest userRequest = new UserRequest();
		userRequest.setId(user.getId());
		userRequest.setUsername(user.getUsername());
		userRequest.setRoleIds(user.getRoles().stream().map(Role::getId).collect(Collectors.toSet()));
		return userRequest;
	}

	@Override
	public Long delete(Long id) {
		userRepository.delete(getUserById(id));
		return id;
	}

}
