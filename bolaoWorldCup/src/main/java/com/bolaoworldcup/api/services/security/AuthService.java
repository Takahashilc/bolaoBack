package com.bolaoworldcup.api.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolaoworldcup.api.dto.log.LogInputDTO;
import com.bolaoworldcup.api.dto.user.UserEmailCodeDTO;
import com.bolaoworldcup.api.dto.user.UserEmailDTO;
import com.bolaoworldcup.api.entities.User;
import com.bolaoworldcup.api.entities.enums.Action;
import com.bolaoworldcup.api.entities.enums.Status;
import com.bolaoworldcup.api.repositories.UserRepository;
import com.bolaoworldcup.api.services.LogService;
import com.bolaoworldcup.api.services.email.EmailService;
import com.bolaoworldcup.api.services.exceptions.ResourceNotFoundException;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenService tokenService;

    public boolean isEmailValidated(UserEmailCodeDTO dto) {
        String userEmail = tokenService.getUserEmail(dto.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getActivationCode().equals(dto.getCode())) {
            user.setActivationCode(null);
            user.setStatus(Status.CONFIRMED);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    public void resendActivationCode(UserEmailCodeDTO dto) {
        String userEmail = tokenService.getUserEmail(dto.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        emailService.sendConfirmationEmail(user);
    }

    public void sendPasswordReset(UserEmailDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        String token = tokenService.generateEmailToken(user);
        emailService.sendPasswordResetEmail(user, token);
    }
}
