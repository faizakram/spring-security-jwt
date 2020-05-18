package com.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.model.User;
import com.spring.repository.IUserRepository;
import com.spring.util.CommonConstant;
import com.spring.util.jwt.JwtUserFactory;
import com.spring.web.exception.ErrorCodeHelper;
import com.spring.web.exception.ErrorInfo;
import com.spring.web.exception.ServiceException;

@Service
@Transactional
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserRepository userRepo;
    

	@Autowired
	private ErrorCodeHelper errorCodeHelper;

    @Override
    public UserDetails loadUserByUsername(String username)	{
        User user = userRepo.findByUsername(username);

        if (user == null) {
        	ErrorInfo errorInfo = errorCodeHelper.getErrorInfo(CommonConstant.E1005_ERROR_CODE,
					CommonConstant.E1005_ERROR_DESCRIPTION);
			throw new ServiceException(errorInfo, HttpStatus.UNAUTHORIZED);
        } else {
            return JwtUserFactory.create(user);
        }
    }
}
