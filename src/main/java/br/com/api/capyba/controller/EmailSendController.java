package br.com.api.capyba.controller;

import br.com.api.capyba.application.EmailSenderService;
import br.com.api.capyba.core.EmailRequest;
import br.com.api.capyba.core.exceptions.EmailServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailSendController {

    private final EmailSenderService emailSenderService;

    public EmailSendController(EmailSenderService emailSenderService){
        this.emailSenderService = emailSenderService;
    }

    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest){
        try{
            this.emailSenderService.sendEmail(emailRequest.to(), emailRequest.subject(), emailRequest.body());
            return ResponseEntity.ok("Email send successfully");
        }catch (EmailServiceException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while sending email");
        }
    }

}
