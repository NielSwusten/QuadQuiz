package fact.it.quadquiz.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.quadquiz.dtos.AnswerCheckDto;
import fact.it.quadquiz.dtos.AnswerResultDto;
import fact.it.quadquiz.dtos.QuestionDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;

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
                    HtmlUtils.htmlUnescape(result.get("correct_answer").asText()));
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
                .append("&type=multiple");

        return url.toString();
    }

    private QuestionDto mapToQuestionDto(JsonNode result) {
        List<String> allAnswers = new ArrayList<>();
        result.get("incorrect_answers").forEach(node ->
                allAnswers.add(HtmlUtils.htmlUnescape(node.asText())));
        allAnswers.add(HtmlUtils.htmlUnescape(result.get("correct_answer").asText()));
        Collections.shuffle(allAnswers);

        return new QuestionDto(
                UUID.randomUUID().toString(),
                HtmlUtils.htmlUnescape(result.get("question").asText()),
                allAnswers,
                HtmlUtils.htmlUnescape(result.get("category").asText()),
                result.get("difficulty").asText()
        );
    }
}
