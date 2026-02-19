package br.com.study_smart_service.adapter.inbound.web.card;

import br.com.study_smart_service.adapter.inbound.web.card.dto.CardDto;
import br.com.study_smart_service.adapter.inbound.web.card.dto.CreateCardDto;
import br.com.study_smart_service.application.usecase.card.CreateCardUseCase;
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

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WebMvcTest(CreateCardController.class)
@AutoConfigureMockMvc(addFilters = false)
class CreateCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateCardUseCase createCardUseCase;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private FindUserByEmailUseCase findUserByEmailUseCase;

    @Test
    void shouldCreateCardSuccessfully() throws Exception {
        UUID deckId = UUID.randomUUID();

        Authentication authentication = UserAuthenticationFactory.userAuthentication();

        CreateCardDto request = new CreateCardDto(
                deckId.toString(),
                "Front text",
                "Back text");

        CardDto response = new CardDto(
                UUID.randomUUID().toString(),
                "Front text",
                "Back text",
                LocalDateTime.now().toString(),
                null);

        Mockito.when(createCardUseCase.execute(
                eq(deckId),
                any(UUID.class),
                eq("Front text"),
                eq("Back text")
        )).thenReturn(response);

        mockMvc.perform(post("/api/v1/card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.front_text").value("Front text"))
                .andExpect(jsonPath("$.back_text").value("Back text"));
    }
}