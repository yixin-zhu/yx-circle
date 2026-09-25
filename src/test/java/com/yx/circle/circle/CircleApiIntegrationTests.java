package com.yx.circle.circle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yx.circle.support.AuthTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CircleApiIntegrationTests {

    /** Seed: user n is member of circle n only — user 77 is not in circle 50. */
    private static final String GUEST_PHONE = "13900000077";
    private static final long FORBIDDEN_CIRCLE_ID = 50L;

    private static final String MEMBER_PHONE = "13900000088";
    /** Separate circle so join test does not pollute forbidden scenario. */
    private static final long JOIN_CIRCLE_ID = 60L;

    private static final String HOST_PHONE = "13900000001";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String memberToken;
    private String hostToken;

    @BeforeEach
    void setUp() throws Exception {
        memberToken = AuthTestSupport.loginAndGetToken(mockMvc, objectMapper, MEMBER_PHONE);
        hostToken = AuthTestSupport.loginAndGetToken(mockMvc, objectMapper, HOST_PHONE);
    }

    @Test
    void listCirclesRequiresLogin() throws Exception {
        mockMvc.perform(get("/api/v1/circles"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void postsForbiddenBeforeJoin() throws Exception {
        String guestToken = AuthTestSupport.loginAndGetToken(mockMvc, objectMapper, GUEST_PHONE);
        mockMvc.perform(get("/api/v1/circles/" + FORBIDDEN_CIRCLE_ID + "/posts")
                        .header("authorization", guestToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(403));
    }

    @Test
    void joinThenReadAndCreatePost() throws Exception {
        mockMvc.perform(post("/api/v1/circles/" + JOIN_CIRCLE_ID + "/join")
                        .header("authorization", memberToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));

        mockMvc.perform(get("/api/v1/circles/" + JOIN_CIRCLE_ID + "/posts")
                        .header("authorization", memberToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.records").isArray());

        mockMvc.perform(post("/api/v1/circles/" + JOIN_CIRCLE_ID + "/posts")
                        .header("authorization", memberToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"test\",\"content\":\"hello\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data").isNumber());
    }

    @Test
    void hostCanUpdateOwnCircle() throws Exception {
        long hostCircleId = 1L;
        mockMvc.perform(put("/api/v1/circles/" + hostCircleId)
                        .header("authorization", hostToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"updated-name\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }

    @Test
    void memberCannotUpdateCircle() throws Exception {
        mockMvc.perform(put("/api/v1/circles/1")
                        .header("authorization", memberToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"hack\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(403));
    }
}
