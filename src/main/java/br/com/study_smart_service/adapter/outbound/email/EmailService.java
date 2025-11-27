package br.com.study_smart_service.adapter.outbound.email;

import java.util.Map;

public interface EmailService {

    void execute(String to, String subject, Map<String, Object> variables, String templatePath);
}
