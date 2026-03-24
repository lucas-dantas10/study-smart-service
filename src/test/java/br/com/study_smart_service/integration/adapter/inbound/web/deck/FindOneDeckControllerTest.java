package br.com.study_smart_service.integration.adapter.inbound.web.deck;

import br.com.study_smart_service.adapter.inbound.web.deck.FindOneDeckController;
import br.com.study_smart_service.adapter.inbound.web.deck.dto.DeckDto;
import br.com.study_smart_service.application.usecase.deck.FindOneDeckByIdAndUserIdUseCase;
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

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(FindOneDeckController.class)
@AutoConfigureMockMvc(addFilters = false)
class FindOneDeckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FindOneDeckByIdAndUserIdUseCase findOneDeckByIdAndUserIdUseCase;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private FindUserByEmailUseCase findUserByEmailUseCase;

    @Test
    void shouldFindOneDeckSuccessfully() throws Exception {
        UUID deckId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Authentication authentication = UserAuthenticationFactory.userAuthentication(userId);

        DeckDto response = new DeckDto(
                deckId.toString(),
                "My Deck",
                null,
                null,
                0,
                0,
                null
        );

        Mockito.when(findOneDeckByIdAndUserIdUseCase.execute(eq(deckId), eq(userId)))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/deck/{deckId}", deckId)
                        .principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(deckId.toString()))
                .andExpect(jsonPath("$.title").value("My Deck"));
    }
}
