package br.com.study_smart_service.integration.adapter.inbound.web.review;

import br.com.study_smart_service.adapter.inbound.web.review.UndoReviewController;
import br.com.study_smart_service.application.usecase.review.UndoReviewUseCase;
import br.com.study_smart_service.application.usecase.user.FindUserByEmailUseCase;
import br.com.study_smart_service.factory.UserAuthenticationFactory;
import br.com.study_smart_service.utils.jwt.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(UndoReviewController.class)
@AutoConfigureMockMvc(addFilters = false)
class UndoReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UndoReviewUseCase undoReviewUseCase;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private FindUserByEmailUseCase findUserByEmailUseCase;

    @Test
    void shouldUndoLastReview() throws Exception {
        UUID cardId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Authentication authentication = UserAuthenticationFactory.userAuthentication(userId);

        mockMvc.perform(delete("/api/v1/review/{cardId}/last", cardId)
                        .principal(authentication))
                .andExpect(status().isNoContent());

        Mockito.verify(undoReviewUseCase).execute(cardId, userId);
    }
}
