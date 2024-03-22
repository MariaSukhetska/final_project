package com.example.final_project.controller;

import com.example.final_project.dto.article.DailyArticleCountDto;
import com.example.final_project.service.StatisticService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
@Tag(name = "Statistics", description = "Endpoint for getting statistic")
@Secured({"ROLE_ADMIN"})
public class StatisticsController {
    private final StatisticService statisticService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/dailyArticleCounts")
    public List<DailyArticleCountDto> getDailyArticleCounts() {
        return statisticService.getDailyArticleCounts();
    }
}
