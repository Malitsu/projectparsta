package fi.tuni.parsta;

import java.util.ArrayList;

public class Progress {
    private int currentLevel;
    private int rightAnswersTotal;
    private int allAnswersTotal;
    private ArrayList<Boolean> currentLevelRightAnswers;
    private ArrayList<Integer> allLevelsRightAnswers;

    public Progress() {
    }

    public Progress(int currentLevel, int rightAnswersTotal, int allAnswersTotal, ArrayList<Boolean> currentLevelRightAnswers, ArrayList<Integer> allLevelsRightAnswers) {
        this.currentLevel = currentLevel;
        this.rightAnswersTotal = rightAnswersTotal;
        this.allAnswersTotal = allAnswersTotal;
        this.currentLevelRightAnswers = currentLevelRightAnswers;
        this.allLevelsRightAnswers = allLevelsRightAnswers;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getRightAnswersTotal() {
        return rightAnswersTotal;
    }

    public void setRightAnswersTotal(int rightAnswersTotal) {
        this.rightAnswersTotal = rightAnswersTotal;
    }

    public int getAllAnswersTotal() {
        return allAnswersTotal;
    }

    public void setAllAnswersTotal(int allAnswersTotal) {
        this.allAnswersTotal = allAnswersTotal;
    }

    public ArrayList<Boolean> getCurrentLevelRightAnswers() {
        return currentLevelRightAnswers;
    }

    public void setCurrentLevelRightAnswers(ArrayList<Boolean> currentLevelRightAnswers) {
        this.currentLevelRightAnswers = currentLevelRightAnswers;
    }

    public ArrayList<Integer> getAllLevelsRightAnswers() {
        return allLevelsRightAnswers;
    }

    public void setAllLevelsRightAnswers(ArrayList<Integer> allLevelsRightAnswers) {
        this.allLevelsRightAnswers = allLevelsRightAnswers;
    }
}
