package fact.it.quadquiz.dtos;

public class AnswerCheckDto {
    private String questionId;
    private String selectedAnswer;

    // Constructors
    public AnswerCheckDto() {}

    public AnswerCheckDto(String questionId, String selectedAnswer) {
        this.questionId = questionId;
        this.selectedAnswer = selectedAnswer;
    }

    // Getters and Setters
    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }
}
