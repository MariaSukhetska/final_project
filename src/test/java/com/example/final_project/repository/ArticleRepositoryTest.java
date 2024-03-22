package com.example.final_project.repository;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "/database/insert_article_test_data.sql")
@Sql(scripts = "/database/remove_articles.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void countByPublishingDate_ShouldReturnCorrectCount() {
        LocalDateTime startDateTime = LocalDateTime.of(2024, 3, 17, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2024, 3, 18, 0, 0);

        Long count = articleRepository.countByPublishingDate(startDateTime, endDateTime);
        assertThat(count).isEqualTo(2);
    }

    @Test
    void countByPublishingDate_ShouldReturnZero_WhenNoArticlesInDateRange() {
        LocalDateTime startDateTime = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2025, 1, 2, 0, 0);
        Long count = articleRepository.countByPublishingDate(startDateTime, endDateTime);
        assertThat(count).isEqualTo(0);
    }

    @Test
    void countByPublishingDate_ShouldIncludeArticlesPublishedOnStartDate() {
        LocalDateTime startDateTime = LocalDateTime.of(2024, 3, 17, 12, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2024, 3, 18, 0, 0);
        Long count = articleRepository.countByPublishingDate(startDateTime, endDateTime);
        assertThat(count).isEqualTo(2); // Assuming there are 2 articles published on 2024-03-17T12:00:00
    }

    @Test
    void countByPublishingDate_ShouldExcludeArticlesPublishedOnEndDate() {
        LocalDateTime startDateTime = LocalDateTime.of(2024, 3, 17, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2024, 3, 18, 10, 0);
        Long count = articleRepository.countByPublishingDate(startDateTime, endDateTime);
        assertThat(count).isEqualTo(2); // Assuming there are 2 articles published before 2024-03-18T10:00:00
    }

    @Test
    void countByPublishingDate_ShouldReturnCorrectCount_ForLargeDateRange() {
        LocalDateTime startDateTime = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2024, 12, 31, 23, 59);
        Long count = articleRepository.countByPublishingDate(startDateTime, endDateTime);
        assertThat(count).isEqualTo(3); // Assuming there are 3 articles published in 2024
    }
}
