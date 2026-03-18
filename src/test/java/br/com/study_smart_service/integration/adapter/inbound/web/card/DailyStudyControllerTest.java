package br.com.study_smart_service.integration.adapter.inbound.web.card;

import br.com.study_smart_service.adapter.inbound.web.card.DailyStudyController;
import br.com.study_smart_service.adapter.inbound.web.card.dto.CardStudyDto;
import br.com.study_smart_service.application.usecase.card.DailyStudyUseCase;
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

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(DailyStudyController.class)
@AutoConfigureMockMvc(addFilters = false)
class DailyStudyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DailyStudyUseCase dailyStudyUseCase;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private FindUserByEmailUseCase findUserByEmailUseCase;

    @Test
    void shouldListDailyStudyCards() throws Exception {
        UUID deckId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Authentication authentication = UserAuthenticationFactory.userAuthentication(userId);

        CardStudyDto card = new CardStudyDto(
                UUID.randomUUID().toString(),
                "Front",
                "Back"
        );

        Mockito.when(dailyStudyUseCase.execute(eq(deckId), eq(userId)))
                .thenReturn(List.of(card));

        mockMvc.perform(get("/api/v1/card/{deckId}/study", deckId)
                        .principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].front_text").value("Front"))
                .andExpect(jsonPath("$[0].back_text").value("Back"));
    }
}
