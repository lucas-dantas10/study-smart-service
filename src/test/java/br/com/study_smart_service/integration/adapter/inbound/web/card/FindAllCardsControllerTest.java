package br.com.study_smart_service.integration.adapter.inbound.web.card;

import br.com.study_smart_service.adapter.inbound.web.card.FindAllCardsController;
import br.com.study_smart_service.adapter.inbound.web.card.dto.FindCardDto;
import br.com.study_smart_service.application.usecase.card.FindAllCardsByDeckIdUseCase;
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

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(FindAllCardsController.class)
@AutoConfigureMockMvc(addFilters = false)
class FindAllCardsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FindAllCardsByDeckIdUseCase findAllCardsByDeckIdUseCase;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private FindUserByEmailUseCase findUserByEmailUseCase;

    @Test
    void shouldFindAllCardsByDeck() throws Exception {
        UUID deckId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Authentication authentication = UserAuthenticationFactory.userAuthentication(userId);

        FindCardDto card = new FindCardDto(
                UUID.randomUUID().toString(),
                "Front text",
                "Back text",
                null,
                null,
                null
        );

        Mockito.when(findAllCardsByDeckIdUseCase.execute(eq(deckId), eq(userId)))
                .thenReturn(List.of(card));

        mockMvc.perform(get("/api/v1/card/{deckId}/deck", deckId)
                        .principal(authentication)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].front_text").value("Front text"))
                .andExpect(jsonPath("$[0].back_text").value("Back text"));
    }
}
