package fi.tuni.parsta;

public class GameImage {
    int id;
    String name;
    int level;
    String infoImg;

    public GameImage() {
    }

    public GameImage(int id, String name, int level, String infoImg) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.infoImg = infoImg;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getInfoImg() {
        return infoImg;
    }

    public void setInfoImg(String infoImg) {
        this.infoImg = infoImg;
    }
}
