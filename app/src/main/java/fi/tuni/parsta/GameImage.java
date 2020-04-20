package fi.tuni.parsta;

public class GameImage {
    int id;
    String name;
    String emotion;

    public GameImage() {
    }

    public GameImage(int id, String name, int level, String infoImg, String emotion) {
        this.id = id;
        this.name = name;
        this.emotion = emotion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getEmotion() {
        return emotion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
