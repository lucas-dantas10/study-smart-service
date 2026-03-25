package br.com.study_smart_service.integration.adapter.inbound.web.deck;

import br.com.study_smart_service.adapter.inbound.web.deck.FindAllDecksController;
import br.com.study_smart_service.adapter.inbound.web.deck.dto.DeckDto;
import br.com.study_smart_service.application.usecase.deck.FindAllDeckByUserIdUseCase;
import br.com.study_smart_service.application.usecase.user.FindUserByEmailUseCase;
import br.com.study_smart_service.factory.UserAuthenticationFactory;
import br.com.study_smart_service.utils.jwt.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(FindAllDecksController.class)
@AutoConfigureMockMvc(addFilters = false)
class FindAllDecksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FindAllDeckByUserIdUseCase findAllDecksByUserIdUseCase;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private FindUserByEmailUseCase findUserByEmailUseCase;

    @Test
    void shouldFindAllDecksByUser() throws Exception {
        UUID userId = UUID.randomUUID();
        Authentication authentication = UserAuthenticationFactory.userAuthentication(userId);

        DeckDto deck = new DeckDto(
                UUID.randomUUID().toString(),
                "My Deck",
                null,
                null,
                10,
                4,
                "2026-03-24T09:30:00"
        );

        Mockito.when(findAllDecksByUserIdUseCase.execute(eq(userId), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(deck), PageRequest.of(0, 10), 1));

        mockMvc.perform(get("/api/v1/deck")
                        .principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("My Deck"))
                .andExpect(jsonPath("$.content[0].total_cards").value(10))
                .andExpect(jsonPath("$.content[0].cards_to_review_today").value(4))
                .andExpect(jsonPath("$.content[0].last_reviewed_at").value("2026-03-24T09:30:00"));
    }
}
