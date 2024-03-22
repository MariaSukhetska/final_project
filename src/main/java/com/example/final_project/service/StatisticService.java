package com.example.final_project.service;

import com.example.final_project.dto.article.DailyArticleCountDto;
import java.util.List;

public interface StatisticService {
    List<DailyArticleCountDto> getDailyArticleCounts();
}
