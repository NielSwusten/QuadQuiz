package fact.it.quadquiz.dtos;

public class AnswerResultDto {
    private String questionId;
    private boolean correct;
    private String correctAnswer;

    // Constructors
    public AnswerResultDto() {}

    public AnswerResultDto(String questionId, boolean correct, String correctAnswer) {
        this.questionId = questionId;
        this.correct = correct;
        this.correctAnswer = correctAnswer;
    }

    // Getters and Setters
    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
