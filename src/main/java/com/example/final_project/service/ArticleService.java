package com.example.final_project.service;

import com.example.final_project.dto.article.ArticleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {
    Page<ArticleDto> getAllArticles(Pageable pageable);

    ArticleDto createArticle(ArticleDto articleDto);

    ArticleDto getArticleById(Long id);
}
