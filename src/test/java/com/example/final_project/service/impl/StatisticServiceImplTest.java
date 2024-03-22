package com.example.final_project.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.final_project.dto.article.DailyArticleCountDto;
import com.example.final_project.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@ExtendWith(MockitoExtension.class)
class StatisticServiceImplTest {

    @InjectMocks
    private StatisticServiceImpl statisticService;

    @Mock
    private ArticleRepository articleRepository;

    @Test
    public void shouldReturnDailyArticleCounts() {
        LocalDate startDate = LocalDate.now().minusDays(6);
        LocalDate endDate = LocalDate.now();
        List<DailyArticleCountDto> expectedCounts = new ArrayList<>();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            Long count = ThreadLocalRandom.current().nextLong(1, 10);
            expectedCounts.add(new DailyArticleCountDto(date, count));
            when(articleRepository.countByPublishingDate(date.atStartOfDay(), date.plusDays(1).atStartOfDay())).thenReturn(count);
        }

        List<DailyArticleCountDto> actualCounts = statisticService.getDailyArticleCounts();

        assertNotNull(actualCounts);
        assertEquals(expectedCounts.size(), actualCounts.size());
        for (int i = 0; i < expectedCounts.size(); i++) {
            assertEquals(expectedCounts.get(i).date(), actualCounts.get(i).date());
            assertEquals(expectedCounts.get(i).count(), actualCounts.get(i).count());
        }
    }

    @Test
    public void shouldReturnZeroCountsWhenNoArticles() {
        LocalDate startDate = LocalDate.now().minusDays(6);
        LocalDate endDate = LocalDate.now();
        List<DailyArticleCountDto> expectedCounts = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            expectedCounts.add(new DailyArticleCountDto(date, 0L));
            when(articleRepository.countByPublishingDate(date.atStartOfDay(), date.plusDays(1).atStartOfDay())).thenReturn(0L);
        }
        List<DailyArticleCountDto> actualCounts = statisticService.getDailyArticleCounts();
        assertEquals(expectedCounts, actualCounts);
    }

    @Test
    public void shouldReturnCorrectDateRange() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(6);
        when(articleRepository.countByPublishingDate(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(5L);
        List<DailyArticleCountDto> counts = statisticService.getDailyArticleCounts();
        assertEquals(7, counts.size());
        assertEquals(startDate, counts.get(0).date());
        assertEquals(endDate, counts.get(6).date());
    }

    @Test
    public void shouldThrowExceptionWhenRepositoryError() {
        LocalDate startDate = LocalDate.now().minusDays(6);
        when(articleRepository.countByPublishingDate(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> statisticService.getDailyArticleCounts());
    }
}
