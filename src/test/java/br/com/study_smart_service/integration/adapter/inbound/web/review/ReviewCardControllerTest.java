package br.com.study_smart_service.integration.adapter.inbound.web.review;

import br.com.study_smart_service.adapter.inbound.web.review.ReviewCardController;
import br.com.study_smart_service.adapter.inbound.web.review.dto.ReviewCardDto;
import br.com.study_smart_service.application.usecase.review.ReviewCardUseCase;
import br.com.study_smart_service.application.usecase.user.FindUserByEmailUseCase;
import br.com.study_smart_service.domain.user.model.User;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(ReviewCardController.class)
@AutoConfigureMockMvc(addFilters = false)
class ReviewCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ReviewCardUseCase reviewCardUseCase;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private FindUserByEmailUseCase findUserByEmailUseCase;

    @Test
    void shouldReviewCardSuccessfully() throws Exception {
        UUID cardId = UUID.randomUUID();
        Authentication authentication = UserAuthenticationFactory.userAuthentication();
        User user = (User) authentication.getPrincipal();

        ReviewCardDto request = new ReviewCardDto(5);

        mockMvc.perform(post("/api/v1/review/{cardId}", cardId)
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        Mockito.verify(reviewCardUseCase).execute(any(User.class), eq(cardId), eq(5));
    }
}
