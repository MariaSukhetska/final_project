package com.example.final_project.mapper;

import com.example.final_project.config.MapperConfiguration;
import com.example.final_project.dto.article.ArticleDto;
import com.example.final_project.model.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfiguration.class)
public interface ArticleMapper {
    @Mapping(target = "id", ignore = true)
    Article toEntity(ArticleDto articleDto);

    ArticleDto toDto(Article article);
}
