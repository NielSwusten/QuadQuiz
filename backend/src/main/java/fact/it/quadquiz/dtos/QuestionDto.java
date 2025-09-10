package fact.it.quadquiz.dtos;

import java.util.List;

public class QuestionDto {

    private String id;
    private String question;
    private List<String> answers;
    private String category;
    private String difficulty;

    public QuestionDto() {}

    public QuestionDto(String id, String question, List<String> answers, String category, String difficulty) {
        this.id = id;
        this.question = question;
        this.answers = answers;
        this.category = category;
        this.difficulty = difficulty;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
