package br.com.api.capyba.core;

public interface EmailSenderUseCase {

    void sendEmail(String to, String subject, String body);

}
