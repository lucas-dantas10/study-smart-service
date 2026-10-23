package br.com.study_smart_service.integration.adapter.inbound.web.card;

import br.com.study_smart_service.adapter.inbound.web.card.BulkCreateCardsController;
import br.com.study_smart_service.application.usecase.card.BulkCreateCardsUseCase;
import br.com.study_smart_service.application.usecase.user.FindUserByEmailUseCase;
import br.com.study_smart_service.factory.UserAuthenticationFactory;
import br.com.study_smart_service.utils.jwt.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(BulkCreateCardsController.class)
@AutoConfigureMockMvc(addFilters = false)
class BulkCreateCardsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BulkCreateCardsUseCase bulkCreateCardsUseCase;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private FindUserByEmailUseCase findUserByEmailUseCase;

    @Test
    void shouldCreateCardsInBulk() throws Exception {
        UUID userId = UUID.randomUUID();
        Authentication authentication = UserAuthenticationFactory.userAuthentication(userId);

        Mockito.when(bulkCreateCardsUseCase.execute(eq(userId), any())).thenReturn(2);

        String body = """
                {
                  "deck_id": "%s",
                  "cards": [
                    {"front_text": "Femur", "back_text": "Osso da coxa", "tags": ["anatomia"]},
                    {"front_text": "Tibia", "back_text": "Osso da perna"}
                  ]
                }
                """.formatted(UUID.randomUUID());

        mockMvc.perform(post("/api/v1/card/bulk")
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.created").value(2));
    }

    @Test
    void shouldRejectEmptyList() throws Exception {
        UUID userId = UUID.randomUUID();
        Authentication authentication = UserAuthenticationFactory.userAuthentication(userId);

        String body = """
                {"deck_id": "%s", "cards": []}
                """.formatted(UUID.randomUUID());

        mockMvc.perform(post("/api/v1/card/bulk")
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void shouldRejectCardWithBlankSide() throws Exception {
        UUID userId = UUID.randomUUID();
        Authentication authentication = UserAuthenticationFactory.userAuthentication(userId);

        String body = """
                {"deck_id": "%s", "cards": [{"front_text": "Femur", "back_text": " "}]}
                """.formatted(UUID.randomUUID());

        mockMvc.perform(post("/api/v1/card/bulk")
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnprocessableEntity());
    }
}
