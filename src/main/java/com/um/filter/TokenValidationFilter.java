package com.um.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.um.entity.UserToken;
import com.um.repository.UserTokenRepository;
import com.um.util.JwtTokenUtility;

@Component
@Order(1)
public class TokenValidationFilter implements Filter {
	
	@Autowired
	private UserTokenRepository userTokenRepository;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpReq = (HttpServletRequest) request;
		
		String uri = httpReq.getRequestURI();
		if(!uri.equals("/rest/v1/user/login")) {
			String token = httpReq.getHeader("auth-token");
			if(StringUtils.isBlank(token)) {
				throw new RuntimeException("Token can't be empty");
			}
			validateToken(token);
		}
		chain.doFilter(request, response);
	}
	
	public void validateToken(String token) {
		Integer userId = JwtTokenUtility.validateToken(token);
		UserToken userToken = userTokenRepository.findByUserId(userId.longValue());
		if(!token.equals(userToken.getToken())) {
			throw new RuntimeException("Invalid Token!");
		}
	}

}
