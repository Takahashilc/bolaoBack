package com.bolaoworldcup.api.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import com.bolaoworldcup.api.config.security.JwtAuthenticationProvider;
import com.bolaoworldcup.api.dto.TokenDTO;
import com.bolaoworldcup.api.dto.log.LogInputDTO;
import com.bolaoworldcup.api.dto.user.UserEmailCodeDTO;
import com.bolaoworldcup.api.dto.user.UserEmailDTO;
import com.bolaoworldcup.api.dto.user.UserLoginDTO;
import com.bolaoworldcup.api.entities.enums.Action;
import com.bolaoworldcup.api.services.LogService;
import com.bolaoworldcup.api.services.UserService;
import com.bolaoworldcup.api.services.security.AuthService;
import com.bolaoworldcup.api.services.security.TokenService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    private JwtAuthenticationProvider authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private LogService logService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody @Valid UserLoginDTO dto) {
        UsernamePasswordAuthenticationToken loginData = dto.toUsernamePasswordAuth();
        try {
            Authentication authentication = authenticationManager.authenticate(loginData);
            String token = tokenService.generateToken(authentication);
            logService.insert(new LogInputDTO(userService.findByEmail((String) loginData.getPrincipal()).getId(), Action.LOGIN));
            return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "email-password-reset")
    public ResponseEntity<?> emailPasswordReset(@RequestBody @Valid UserEmailDTO dto) {
        authService.sendPasswordReset(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/activation-code")
    public ResponseEntity<?> activationCode(@RequestBody @Valid UserEmailCodeDTO dto) {
        boolean notValid = !(authService.isEmailValidated(dto));

        if (notValid) {
            return new ResponseEntity<Object>("Código de ativação inválido", new HttpHeaders(), HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/resend-activation-code")
    public ResponseEntity<?> resendActivationCode(@RequestBody @Valid UserEmailCodeDTO dto) {
        authService.resendActivationCode(dto);
        return ResponseEntity.ok().build();
    }
}
