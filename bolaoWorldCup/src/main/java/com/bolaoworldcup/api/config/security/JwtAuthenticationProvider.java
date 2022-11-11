package com.bolaoworldcup.api.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.bolaoworldcup.api.entities.User;
import com.bolaoworldcup.api.entities.enums.Status;
import com.bolaoworldcup.api.services.UserService;
import com.bolaoworldcup.api.services.security.TokenService;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        User user = userService.findByEmail(email);

        if (user.getStatus().equals(Status.PENDING)) {
            throw new BadCredentialsException("Usuário pendente de confirmação de email!");
        }

        if (!userService.passwordsMatch(password, user.getPassword())) {
            throw new BadCredentialsException("Senha inválida!");
        }

        return new UsernamePasswordAuthenticationToken(email, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
