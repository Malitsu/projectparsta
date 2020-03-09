package fi.tuni.parsta;

public class GameImage {
    int id;
    String name;
    AnswerOption correctAnswer;
    String infoImg;

    public GameImage(int id, String name, AnswerOption correctAnswer, String infoImg) {
        setId(id);
        setName(name);
        setCorrectAnswer(correctAnswer);
        setInfoImg(infoImg);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AnswerOption getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(AnswerOption correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getInfoImg() {
        return infoImg;
    }

    public void setInfoImg(String infoImg) {
        this.infoImg = infoImg;
    }
}
