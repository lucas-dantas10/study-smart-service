package br.com.study_smart_service.integration.adapter.inbound.web.deck;

import br.com.study_smart_service.adapter.inbound.web.deck.CreateDeckController;
import br.com.study_smart_service.adapter.inbound.web.deck.dto.CreateDeckDto;
import br.com.study_smart_service.adapter.inbound.web.deck.dto.DeckDto;
import br.com.study_smart_service.application.usecase.deck.CreateDeckUseCase;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(CreateDeckController.class)
@AutoConfigureMockMvc(addFilters = false)
class CreateDeckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateDeckUseCase createDeckUseCase;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private FindUserByEmailUseCase findUserByEmailUseCase;

    @Test
    void shouldCreateDeckSuccessfully() throws Exception {
        UUID userId = UUID.randomUUID();
        Authentication authentication = UserAuthenticationFactory.userAuthentication(userId);

        CreateDeckDto request = new CreateDeckDto("My Deck");
        DeckDto response = new DeckDto(
                UUID.randomUUID().toString(),
                "My Deck",
                null,
                null,
                0,
                0,
                null
        );

        Mockito.when(createDeckUseCase.execute(eq(userId), eq("My Deck")))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/deck")
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("My Deck"));
    }
}
