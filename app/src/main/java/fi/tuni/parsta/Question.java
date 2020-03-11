package fi.tuni.parsta;

import java.util.ArrayList;

public class Question {
    int id;
    String img_id;
    int numberOfOptions;
    ArrayList<String> answerOptions;

    public Question(){

    }

    public Question(int id, String img_id, int numberOfOptions){
        setId(id);
        setImg_id(img_id);
        setNumberOfOptions(numberOfOptions);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg_id() {
        return img_id;
    }

    public void setImg_id(String img_id) {
        this.img_id = img_id;
    }

    public int getNumberOfOptions() {
        return numberOfOptions;
    }

    public void setNumberOfOptions(int numberOfOptions) {
        this.numberOfOptions = numberOfOptions;
    }

    public ArrayList<String> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(ArrayList<String> answerOptions) {
        this.answerOptions = answerOptions;
    }
}
