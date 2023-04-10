package com.sigmamales.sigmafoodserver.api.controller;

import com.sigmamales.sigmafoodserver.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(TokenController.BASE_PATH)
public class TokenController {

	public static final String BASE_PATH = "/token";

	private final TokenService tokenService;

	@PostMapping
	public String createToken() {
		return tokenService.createToken();
	}

}
