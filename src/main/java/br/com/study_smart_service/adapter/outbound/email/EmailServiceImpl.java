package br.com.study_smart_service.adapter.outbound.email;

import br.com.study_smart_service.utils.logger.LogUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public void execute(String to, String subject, Map<String, Object> variables, String templatePath) {
        LogUtils.log(this.getClass().toString(), "starts send email", false);

        try {
            Context context = new Context();
            context.setVariables(variables);

            String html = templateEngine.process(templatePath, context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    "UTF-8");

          mimeMessageHelper.setTo(to);
          mimeMessageHelper.setSubject(subject);
          mimeMessageHelper.setText(html, true);

          mailSender.send(message);
        } catch (MessagingException e) {
            LogUtils.log(this.getClass().toString(), "error sending email", true);
            return;
        }

        LogUtils.log(this.getClass().toString(), "finish send email", false);
    }
}
