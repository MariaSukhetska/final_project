package com.example.final_project.service.impl;

import com.example.final_project.dto.article.ArticleDto;
import com.example.final_project.exception.EntityNotFoundException;
import com.example.final_project.mapper.ArticleMapper;
import com.example.final_project.model.Article;
import com.example.final_project.repository.ArticleRepository;
import com.example.final_project.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    @Override
    public Page<ArticleDto> getAllArticles(Pageable pageable) {
        return articleRepository
                .findAll(pageable)
                .map(articleMapper::toDto);
    }

    @Override
    public ArticleDto createArticle(ArticleDto articleDto) {
        Article article = articleMapper.toEntity(articleDto);
        Article savedArticle = articleRepository.save(article);
        return articleMapper.toDto(savedArticle);
    }

    @Override
    public ArticleDto getArticleById(Long id) {
        return articleRepository
                .findById(id)
                .map(articleMapper::toDto).orElseThrow(() ->
                        new EntityNotFoundException("Article not found with id: " + id));
    }
}
