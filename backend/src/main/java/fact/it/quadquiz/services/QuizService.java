package fact.it.quadquiz.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.quadquiz.dtos.AnswerCheckDto;
import fact.it.quadquiz.dtos.AnswerResultDto;
import fact.it.quadquiz.dtos.QuestionDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, String> correctAnswers = new HashMap<>();

    public List<QuestionDto> getQuestions(int amount, String category, String difficulty) throws Exception {

        String apiUrl = buildApiUrl(amount, category, difficulty);

        String response = restTemplate.getForObject(apiUrl, String.class);
        JsonNode jsonNode = objectMapper.readTree(response);

        List<QuestionDto> questions = new ArrayList<>();
        JsonNode results = jsonNode.get("results");

        for (JsonNode result : results) {
            QuestionDto questionDto = mapToQuestionDto(result);
            questions.add(questionDto);

            correctAnswers.put(questionDto.getId(),
                    URLDecoder.decode(result.get("correct_answer").asText(), StandardCharsets.UTF_8));
        }

        return questions;
    }

    public List<AnswerResultDto> checkAnswers(List<AnswerCheckDto> userAnswers) {
        return userAnswers.stream()
                .map(answer -> {
                    String correctAnswer = correctAnswers.get(answer.getQuestionId());
                    boolean isCorrect = correctAnswer != null &&
                            correctAnswer.equalsIgnoreCase(answer.getSelectedAnswer().trim());

                    return new AnswerResultDto(
                            answer.getQuestionId(),
                            isCorrect,
                            correctAnswer
                    );
                })
                .collect(Collectors.toList());
    }

    private String buildApiUrl(int amount, String category, String difficulty) {
        StringBuilder url = new StringBuilder("https://opentdb.com/api.php?amount=" + amount);

        if (category != null && !category.isEmpty()) {
            url.append("&category=").append(category);
        }

        url.append("&difficulty=").append(difficulty)
                .append("&type=multiple")
                .append("&encode=url3986"); // URL encoding voor speciale karakters

        return url.toString();
    }

    private QuestionDto mapToQuestionDto(JsonNode result) throws Exception {
        String questionId = UUID.randomUUID().toString();
        String question = URLDecoder.decode(result.get("question").asText(), StandardCharsets.UTF_8);
        String correctAnswer = URLDecoder.decode(result.get("correct_answer").asText(), StandardCharsets.UTF_8);

        List<String> incorrectAnswers = new ArrayList<>();
        JsonNode incorrectArray = result.get("incorrect_answers");
        for (JsonNode incorrect : incorrectArray) {
            incorrectAnswers.add(URLDecoder.decode(incorrect.asText(), StandardCharsets.UTF_8));
        }

        List<String> allAnswers = new ArrayList<>(incorrectAnswers);
        allAnswers.add(correctAnswer);
        Collections.shuffle(allAnswers);

        return new QuestionDto(
                questionId,
                question,
                allAnswers,
                URLDecoder.decode(result.get("category").asText(), StandardCharsets.UTF_8),
                URLDecoder.decode(result.get("difficulty").asText(), StandardCharsets.UTF_8)
        );
    }
}
