package br.com.api.capyba.models;



import java.util.Objects;


public class EmailModel {

    private String recipient;
    private String messageBody;
    private String subject;
    private String attachment;

    public EmailModel() {
    }

    public EmailModel(String recipient, String messageBody, String subject, String attachment) {
        this.recipient = recipient;
        this.messageBody = messageBody;
        this.subject = subject;
        this.attachment = attachment;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
