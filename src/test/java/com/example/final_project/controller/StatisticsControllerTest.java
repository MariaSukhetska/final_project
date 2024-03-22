package com.example.final_project.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.final_project.dto.article.DailyArticleCountDto;
import com.example.final_project.security.JwtUtil;
import com.example.final_project.service.StatisticService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class StatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatisticService statisticService;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getDailyArticleCountsShouldReturnListOfCounts() throws Exception {
        List<DailyArticleCountDto> counts = List.of(
                new DailyArticleCountDto(LocalDate.now(), 10L),
                new DailyArticleCountDto(LocalDate.now().minusDays(1), 5L)
        );
        when(statisticService.getDailyArticleCounts()).thenReturn(counts);

        mockMvc.perform(get("/statistics/dailyArticleCounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].date", is(LocalDate.now().toString())))
                .andExpect(jsonPath("$[0].count", is(10)))
                .andExpect(jsonPath("$[1].date", is(LocalDate.now().minusDays(1).toString())))
                .andExpect(jsonPath("$[1].count", is(5)));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getDailyArticleCountsShouldReturnForbiddenForNonAdmin() throws Exception {
        mockMvc.perform(get("/statistics/dailyArticleCounts"))
                .andExpect(status().isForbidden());
    }
}
