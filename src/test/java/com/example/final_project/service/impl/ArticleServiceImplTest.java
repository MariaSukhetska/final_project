package com.example.final_project.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.final_project.dto.article.ArticleDto;
import com.example.final_project.exception.EntityNotFoundException;
import com.example.final_project.mapper.ArticleMapper;
import com.example.final_project.model.Article;
import com.example.final_project.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {

    @InjectMocks
    private ArticleServiceImpl articleService;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private ArticleMapper articleMapper;

    private Article testArticle;
    private ArticleDto testArticleDto;

    @BeforeEach
    void setUp() {
        testArticle = new Article();
        testArticle.setId(1L);
        testArticle.setTitle("Test Title");
        testArticle.setAuthor("Test Author");
        testArticle.setContent("Test Content");
        testArticle.setPublishingDate(LocalDateTime.now());

        testArticleDto = new ArticleDto()
                .setTitle("Test Title")
                .setAuthor("Test Author")
                .setContent("Test Content")
                .setPublishingDate(LocalDateTime.now());
    }

    @Test
    void shouldCreateArticle() {
        when(articleMapper.toEntity(any(ArticleDto.class))).thenReturn(testArticle);
        when(articleRepository.save(any(Article.class))).thenReturn(testArticle);
        when(articleMapper.toDto(any(Article.class))).thenReturn(testArticleDto);

        ArticleDto createdArticleDto = articleService.createArticle(testArticleDto);

        assertNotNull(createdArticleDto);
        assertEquals(testArticleDto.getTitle(), createdArticleDto.getTitle());
        verify(articleMapper, times(1)).toEntity(any(ArticleDto.class));
        verify(articleRepository, times(1)).save(any(Article.class));
        verify(articleMapper, times(1)).toDto(any(Article.class));
    }

    @Test
    void shouldGetArticleById() {
        when(articleRepository.findById(anyLong())).thenReturn(Optional.of(testArticle));
        when(articleMapper.toDto(any(Article.class))).thenReturn(testArticleDto);

        ArticleDto foundArticleDto = articleService.getArticleById(1L);

        assertNotNull(foundArticleDto);
        assertEquals(testArticleDto.getTitle(), foundArticleDto.getTitle());
        verify(articleRepository, times(1)).findById(anyLong());
        verify(articleMapper, times(1)).toDto(any(Article.class));
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenArticleNotFound() {
        when(articleRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> articleService.getArticleById(1L));
    }

    @Test
    void shouldGetAllArticles() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Article> articles = List.of(testArticle);
        Page<Article> articlePage = new PageImpl<>(articles, pageable, articles.size());

        when(articleRepository.findAll(any(Pageable.class))).thenReturn(articlePage);
        when(articleMapper.toDto(any(Article.class))).thenReturn(testArticleDto);

        Page<ArticleDto> resultPage = articleService.getAllArticles(pageable);

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getContent().size());
        assertEquals(testArticleDto.getTitle(), resultPage.getContent().get(0).getTitle());
        verify(articleRepository, times(1)).findAll(any(Pageable.class));
        verify(articleMapper, times(1)).toDto(any(Article.class));
    }
}
