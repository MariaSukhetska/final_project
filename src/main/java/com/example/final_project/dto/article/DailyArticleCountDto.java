package com.example.final_project.dto.article;

import java.time.LocalDate;

public record DailyArticleCountDto(
        LocalDate date,
        Long count
) {}
