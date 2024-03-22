package com.example.final_project.repository;

import com.example.final_project.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT COUNT(a) FROM Article a WHERE a.publishingDate >= :startDateTime AND a.publishingDate < :endDateTime")
    Long countByPublishingDate(@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime);
}
