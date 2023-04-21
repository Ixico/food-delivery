package com.sigmamales.sigmafoodserver.controller;

import com.sigmamales.sigmafoodserver.api.dto.TokenDto;
import com.sigmamales.sigmafoodserver.service.TokenService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(TokenController.BASE_PATH)
public class TokenController {

	public static final String BASE_PATH = "/token";

	private final TokenService tokenService;


	@PostMapping
	public TokenDto createTokens() {
		return tokenService.createTokens();
	}

	@DeleteMapping
	public void revokeToken(@NotBlank @RequestParam String token) {
		tokenService.revokeToken(token);
	}

}
