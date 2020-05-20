package com.spring.contoller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dto.ResponseJson;
import com.spring.dto.UserRequest;
import com.spring.service.UserService;
import com.spring.util.CommonConstant;
import com.spring.util.MappingConstant;

@RestController
@RequestMapping(MappingConstant.USER)
public class UserController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private ResponseJson response;

	@Resource(name = CommonConstant.COMMON_MAP_OBJECT)
	private Map<String, Object> model;

	/**
	 * 
	 * @param userRequest
	 * @return
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(MappingConstant.ADD)
	public ResponseJson addUser(@RequestBody UserRequest userRequest) {
		response.setResponseDescription(CommonConstant.S0002_SUCCESS_DESCRIPTION);
		model.put(CommonConstant.ID, userService.addUser(userRequest));
		response.setResponse(model);
		return response;
	}

	/**
	 * Update Author
	 * 
	 * @param author
	 * @return
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(MappingConstant.UPDATE)
	public ResponseJson updateUser(@RequestBody UserRequest userRequest) {
		response.setResponseDescription(CommonConstant.S0003_SUCCESS_DESCRIPTION);
		model.put(CommonConstant.ID, userService.updateUser(userRequest));
		response.setResponse(model);
		return response;
	}

	/**
	 * Author List
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@GetMapping(MappingConstant.LIST)
	public ResponseJson getUsers() {
		response.setResponseDescription(CommonConstant.S0001_SUCCESS_DESCRIPTION);
		model.put(CommonConstant.RESULTS, userService.getUsers());
		response.setResponse(model);
		return response;
	}

	/**
	 * Author By Id
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@GetMapping(MappingConstant.COMMON_ID)
	public ResponseJson getUser(@PathVariable Long id) {
		response.setResponseDescription(CommonConstant.S0001_SUCCESS_DESCRIPTION);
		model.put(CommonConstant.RESULTS, userService.getUser(id));
		response.setResponse(model);
		return response;
	}

	/**
	 * Delete Author By Id
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(MappingConstant.COMMON_ID)
	public ResponseJson delete(@PathVariable Long id) {
		response.setResponseDescription(CommonConstant.S0004_SUCCESS_DESCRIPTION);
		model.put(CommonConstant.ID, userService.delete(id));
		response.setResponse(model);
		return response;
	}
	
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@GetMapping(MappingConstant.LIST_EMP)
	public ResponseJson getEmployees() {
		response.setResponseDescription(CommonConstant.S0001_SUCCESS_DESCRIPTION);
		model.put(CommonConstant.RESULTS, userService.getEmployees());
		response.setResponse(model);
		return response;
	}

	
}
