package br.com.api.capyba.controller;

import br.com.api.capyba.models.EmailModel;
import br.com.api.capyba.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping
    public void enviarEmail(@RequestBody EmailModel emailModel) {
        emailService.sendEmail(emailModel);
    }
}
