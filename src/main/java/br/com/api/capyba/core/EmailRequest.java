package br.com.api.capyba.core;

public record EmailRequest(String to, String subject, String body) {
}
