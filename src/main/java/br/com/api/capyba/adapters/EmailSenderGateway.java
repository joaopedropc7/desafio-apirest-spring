package br.com.api.capyba.adapters;

public interface EmailSenderGateway {

    void sendEmail(String to, String subject, String body);

}
