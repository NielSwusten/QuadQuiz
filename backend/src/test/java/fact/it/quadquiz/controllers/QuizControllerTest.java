package fact.it.quadquiz.controllers;

import fact.it.quadquiz.dtos.AnswerCheckDto;
import fact.it.quadquiz.dtos.AnswerResultDto;
import fact.it.quadquiz.dtos.QuestionDto;
import fact.it.quadquiz.services.QuizService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


class QuizControllerTest {

        @Mock
        private QuizService quizService;

        @InjectMocks
        private QuizController quizController;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void getQuestions_Success_ShouldReturnOkWithQuestions() throws Exception {
            // Arrange
            List<QuestionDto> mockQuestions = Arrays.asList(
                    new QuestionDto("1", "What is the capital of France?",
                            Arrays.asList("Paris", "London", "Berlin", "Madrid"),
                            "Geography", "easy"),
                    new QuestionDto("2", "What is 2+2?",
                            Arrays.asList("4", "3", "5", "6"),
                            "Math", "easy")
            );

            when(quizService.getQuestions(10, null, "easy")).thenReturn(mockQuestions);

            // Act
            ResponseEntity<List<QuestionDto>> response = quizController.getQuestions(10, null, "easy");

            // Assert
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(mockQuestions, response.getBody());
            assertEquals(2, response.getBody().size());
            verify(quizService, times(1)).getQuestions(10, null, "easy");
        }

        @Test
        void getQuestions_ServiceThrowsException_ShouldReturnInternalServerError() throws Exception {
            // Arrange
            when(quizService.getQuestions(anyInt(), anyString(), anyString()))
                    .thenThrow(new RuntimeException("API unavailable"));

            // Act
            ResponseEntity<List<QuestionDto>> response = quizController.getQuestions(5, "science", "medium");

            // Assert
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertNull(response.getBody());
            verify(quizService, times(1)).getQuestions(5, "science", "medium");
        }

        @Test
        void checkAnswers_Success_ShouldReturnOkWithResults() {
            // Arrange
            List<AnswerCheckDto> userAnswers = Arrays.asList(
                    new AnswerCheckDto("1", "Paris"),
                    new AnswerCheckDto("2", "4")
            );

            List<AnswerResultDto> mockResults = Arrays.asList(
                    new AnswerResultDto("1", true, "Paris"),
                    new AnswerResultDto("2", true, "4")
            );

            when(quizService.checkAnswers(userAnswers)).thenReturn(mockResults);

            // Act
            ResponseEntity<List<AnswerResultDto>> response = quizController.checkAnswers(userAnswers);

            // Assert
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(mockResults, response.getBody());
            assertEquals(2, response.getBody().size());
            assertTrue(response.getBody().get(0).isCorrect());
            assertTrue(response.getBody().get(1).isCorrect());
            verify(quizService, times(1)).checkAnswers(userAnswers);
        }
    }