package com.example.final_project.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.final_project.dto.article.ArticleDto;
import com.example.final_project.exception.EntityNotFoundException;
import com.example.final_project.security.JwtUtil;
import com.example.final_project.service.ArticleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private JwtUtil jwtUtil;

    @WithMockUser(roles = "USER")
    @Test
    void getAllArticles() throws Exception {
        Page<ArticleDto> page = new PageImpl<>(List.of(new ArticleDto().setTitle("Test Article")
                .setAuthor("Test Author").setContent("Test Content").setPublishingDate(LocalDateTime.now())));
        when(articleService.getAllArticles(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title", is("Test Article")))
                .andExpect(jsonPath("$.content[0].author", is("Test Author")))
                .andExpect(jsonPath("$.content[0].content", is("Test Content")));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void createArticleShouldReturnCreatedArticle() throws Exception {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setTitle("Test Title");
        articleDto.setAuthor("Test Author");
        articleDto.setContent("Test Content");
        articleDto.setPublishingDate(LocalDateTime.now());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String articleDtoJson = objectMapper.writeValueAsString(articleDto);

        when(articleService.createArticle(any(ArticleDto.class))).thenReturn(articleDto);

        mockMvc.perform(post("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(articleDtoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Test Title")))
                .andExpect(jsonPath("$.author", is("Test Author")))
                .andExpect(jsonPath("$.content", is("Test Content")))
                .andExpect(jsonPath("$.publishingDate", notNullValue()));
    }

    @WithMockUser(roles = "USER")
    @Test
    void getArticleById() throws Exception {
        ArticleDto articleDto = new ArticleDto().setTitle("Article").setAuthor("Author").setContent("Content")
                .setPublishingDate(LocalDateTime.now());
        when(articleService.getArticleById(1L)).thenReturn(articleDto);

        mockMvc.perform(get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Article")))
                .andExpect(jsonPath("$.author", is("Author")))
                .andExpect(jsonPath("$.content", is("Content")));
    }


    @WithMockUser(roles = "USER")
    @Test
    void getArticleByIdShouldReturnNotFoundWhenArticleDoesNotExist() throws Exception {
        when(articleService.getArticleById(999L)).thenThrow(new EntityNotFoundException("Article not found"));
        mockMvc.perform(get("/articles/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Article not found")));
    }

    @WithMockUser
    @Test
    void createArticleShouldReturnForbiddenForNonAdminUser() throws Exception {
        ArticleDto articleDto = new ArticleDto()
                .setTitle("Test Title")
                .setAuthor("Test Author")
                .setContent("Test Content")
                .setPublishingDate(LocalDateTime.now());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String articleDtoJson = objectMapper.writeValueAsString(articleDto);

        mockMvc.perform(post("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(articleDtoJson))
                .andExpect(status().isForbidden());
    }
}
