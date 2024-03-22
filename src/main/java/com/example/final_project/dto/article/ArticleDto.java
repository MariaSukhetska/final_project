package com.example.final_project.dto.article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Accessors(chain = true)
@Data
public class ArticleDto {
    @NotBlank
    @Size(max = 100)
    private String title;
    @NotBlank
    private String author;
    @NotBlank
    private String content;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime publishingDate;
}
