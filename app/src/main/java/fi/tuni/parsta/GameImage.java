package fi.tuni.parsta;

public class GameImage {
    int id;
    String name;
    String emotionFi;
    String emotionEn;

    public GameImage() {
    }

    public GameImage(int id, String name, int level, String infoImg, String emotionFi, String emotionEn) {
        this.id = id;
        this.name = name;
        this.emotionFi = emotionFi;
        this.emotionEn = emotionEn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmotionFi() {
        return emotionFi;
    }

    public void setEmotionFi(String emotionFi) {
        this.emotionFi = emotionFi;
    }

    public String getEmotionEn() {
        return emotionEn;
    }

    public void setEmotionEn(String emotionEn) {
        this.emotionEn = emotionEn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
