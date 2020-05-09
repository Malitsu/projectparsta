package fi.tuni.facingo;

import java.util.Arrays;

public class Level {
    private int id;
    private int[] faces;
    private int choices;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[] getFaces() {
        return faces;
    }

    public void setFaces(int[] faces) {
        this.faces = faces;
    }

    public int getChoices() {
        return choices;
    }

    public void setChoices(int choices) {
        this.choices = choices;
    }

    @Override
    public String toString() {
        return "Level{" +
                "id=" + id +
                ", faces=" + Arrays.toString(faces) +
                ", choices=" + choices +
                '}';
    }
}
