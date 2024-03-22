package com.example.final_project.service.impl;

import com.example.final_project.dto.article.DailyArticleCountDto;
import com.example.final_project.repository.ArticleRepository;
import com.example.final_project.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StatisticServiceImpl implements StatisticService {
    private final ArticleRepository articleRepository;

    @Override
    public List<DailyArticleCountDto> getDailyArticleCounts() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(6);

        List<DailyArticleCountDto> counts = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            Long count = articleRepository.countByPublishingDate(date.atStartOfDay(),
                    date.plusDays(1).atStartOfDay());
            counts.add(new DailyArticleCountDto(date, count));
        }
        return counts;
    }
}
