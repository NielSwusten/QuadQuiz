package fact.it.quadquiz.service;


import fact.it.quadquiz.dtos.AnswerCheckDto;
import fact.it.quadquiz.dtos.AnswerResultDto;
import fact.it.quadquiz.services.QuizService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class QuizServiceTest {

    private QuizService quizService;

    @BeforeEach
    void setUp() {
        quizService = new QuizService();
    }

    @Test
    void checkAnswers_WithCorrectAnswer_ShouldReturnCorrectResult() {
        // Arrange
        Map<String, String> correctAnswers = new HashMap<>();
        correctAnswers.put("question1", "Paris");
        correctAnswers.put("question2", "4");

        // Gebruik ReflectionTestUtils om de private field te zetten
        ReflectionTestUtils.setField(quizService, "correctAnswers", correctAnswers);

        List<AnswerCheckDto> userAnswers = Arrays.asList(
                new AnswerCheckDto("question1", "Paris"),
                new AnswerCheckDto("question2", "4")
        );

        // Act
        List<AnswerResultDto> results = quizService.checkAnswers(userAnswers);

        // Assert
        assertEquals(2, results.size());
        assertTrue(results.get(0).isCorrect());
        assertEquals("Paris", results.get(0).getCorrectAnswer());
        assertTrue(results.get(1).isCorrect());
        assertEquals("4", results.get(1).getCorrectAnswer());
    }

    @Test
    void checkAnswers_WithIncorrectAnswer_ShouldReturnIncorrectResult() {
        // Arrange
        Map<String, String> correctAnswers = new HashMap<>();
        correctAnswers.put("question1", "Paris");

        ReflectionTestUtils.setField(quizService, "correctAnswers", correctAnswers);

        List<AnswerCheckDto> userAnswers = Arrays.asList(
                new AnswerCheckDto("question1", "London")
        );

        // Act
        List<AnswerResultDto> results = quizService.checkAnswers(userAnswers);

        // Assert
        assertEquals(1, results.size());
        assertFalse(results.get(0).isCorrect());
        assertEquals("Paris", results.get(0).getCorrectAnswer());
    }

    @Test
    void checkAnswers_WithCaseInsensitiveAnswer_ShouldReturnCorrectResult() {
        // Arrange
        Map<String, String> correctAnswers = new HashMap<>();
        correctAnswers.put("question1", "Paris");

        ReflectionTestUtils.setField(quizService, "correctAnswers", correctAnswers);

        List<AnswerCheckDto> userAnswers = Arrays.asList(
                new AnswerCheckDto("question1", "PARIS"),
                new AnswerCheckDto("question1", "paris")
        );

        // Act
        List<AnswerResultDto> results = quizService.checkAnswers(userAnswers);

        // Assert
        assertEquals(2, results.size());
        assertTrue(results.get(0).isCorrect());
        assertTrue(results.get(1).isCorrect());
    }
    @Test
    void checkAnswers_WithEmptyList_ShouldReturnEmptyResults() {
        // Arrange
        List<AnswerCheckDto> userAnswers = Arrays.asList();

        // Act
        List<AnswerResultDto> results = quizService.checkAnswers(userAnswers);

        // Assert
        assertTrue(results.isEmpty());
    }
}