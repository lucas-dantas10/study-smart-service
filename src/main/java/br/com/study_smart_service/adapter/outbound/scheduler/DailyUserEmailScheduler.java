package br.com.study_smart_service.adapter.outbound.scheduler;

import br.com.study_smart_service.adapter.outbound.email.EmailService;
import br.com.study_smart_service.domain.card.model.Card;
import br.com.study_smart_service.domain.card.repository.CardRepository;
import br.com.study_smart_service.domain.user.model.User;
import br.com.study_smart_service.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class DailyUserEmailScheduler {

    private static final String SUBJECT = "Cards para estudar";

    private final EmailService emailService;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;

//    "0 0 8 * * *", zone = "America/Sao_Paulo"
    @Scheduled(fixedDelay = 5000)
    @Transactional(readOnly = true)
    public void execute() {
        List<User> users = userRepository.findAll();

        users.forEach(user -> {
            List<Card> cards = cardRepository.findAllStudyCardsByUserId(user.getId());

            if (cards.isEmpty()) {
                return;
            }

            // TODO: Montar o body

            CompletableFuture.runAsync(() -> {
                emailService.execute(user.getEmail(), SUBJECT, "body");
            });
        });
    }
}
