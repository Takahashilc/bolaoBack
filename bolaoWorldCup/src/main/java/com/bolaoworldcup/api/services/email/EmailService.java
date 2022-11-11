package com.bolaoworldcup.api.services.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.bolaoworldcup.api.entities.User;

@Service
public class EmailService {

    @Value("${mail.from}")
    private String emailFrom;

    @Value("${url}")
    private String url;

    @Autowired
    private JavaMailSender emailSender;

    public void sendMailMessage(final SimpleMailMessage simpleMailMessage) {
        this.emailSender.send(simpleMailMessage);
    }

    @Async
    public void sendConfirmationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(user.getEmail());
        message.setSubject("Bolão Copa do Mundo 2022 - Confirma Email");
        message.setText("Olá, "
                + user.getNickname() + "!\n\n"
                + "Código recebido: " + user.getActivationCode() + "\n\n"
                + "Divirta-se,\n" + "Bolão Copa do Mundo 2022");

        this.sendMailMessage(message);
    }

    @Async
    public void sendPasswordResetEmail(User user, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(user.getEmail());
        message.setSubject("Bolão Copa do Mundo 2022 - Altera Senha");
        message.setText("Olá, "
                + user.getNickname() + "!\n\n"
                + "Link para alteração de senha:\n" + url + "/auth/password-reset/" + token + "\n\n"
                + "Atenciosamente,\n" + "Bolão Copa do Mundo 2022");

        this.sendMailMessage(message);
    }
}