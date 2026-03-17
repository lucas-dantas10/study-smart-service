package br.com.study_smart_service.integration.adapter.inbound.web.card;

import br.com.study_smart_service.adapter.inbound.web.card.UpdateCardController;
import br.com.study_smart_service.adapter.inbound.web.card.dto.CardDto;
import br.com.study_smart_service.application.usecase.card.UpdateCardUseCase;
import br.com.study_smart_service.application.usecase.user.FindUserByEmailUseCase;
import br.com.study_smart_service.factory.UserAuthenticationFactory;
import br.com.study_smart_service.utils.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(UpdateCardController.class)
@AutoConfigureMockMvc(addFilters = false)
class UpdateCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UpdateCardUseCase updateCardUseCase;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private FindUserByEmailUseCase findUserByEmailUseCase;

    @Test
    void shouldUpdateCardSuccessfully() throws Exception {
        UUID cardId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Authentication authentication = UserAuthenticationFactory.userAuthentication(userId);

        CardDto request = new CardDto(
                cardId.toString(),
                "Updated Front",
                "Updated Back",
                null,
                null
        );

        Mockito.when(updateCardUseCase.execute(
                eq(cardId),
                eq(userId),
                eq("Updated Front"),
                eq("Updated Back")
        )).thenReturn(request);

        mockMvc.perform(put("/api/v1/card/{cardId}", cardId)
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.front_text").value("Updated Front"))
                .andExpect(jsonPath("$.back_text").value("Updated Back"));
    }
}
