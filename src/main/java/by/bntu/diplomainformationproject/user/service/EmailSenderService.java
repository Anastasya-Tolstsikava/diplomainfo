package by.bntu.diplomainformationproject.user.service;

public interface EmailSenderService {
    void send(String to, String subject, String text);
}
