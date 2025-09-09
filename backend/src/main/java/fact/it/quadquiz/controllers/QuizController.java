package fact.it.quadquiz.controllers;

import fact.it.quadquiz.dtos.AnswerCheckDto;
import fact.it.quadquiz.dtos.AnswerResultDto;
import fact.it.quadquiz.dtos.QuestionDto;
import fact.it.quadquiz.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/questions")
    public ResponseEntity<List<QuestionDto>> getQuestions(
            @RequestParam(defaultValue = "10") int amount,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "easy") String difficulty) {

        try {
            List<QuestionDto> questions = quizService.getQuestions(amount, category, difficulty);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/checkanswers")
    public ResponseEntity<List<AnswerResultDto>> checkAnswers(@RequestBody List<AnswerCheckDto> answers) {
        try {
            List<AnswerResultDto> results = quizService.checkAnswers(answers);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


}
