package br.com.study_smart_service.adapter.outbound.email;

public interface EmailService {

    void execute(String to, String subject, String body);
}
