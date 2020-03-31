package fi.tuni.parsta;

import android.util.Log;

import java.util.ArrayList;

public class Level {
    int level;
    int score;
    int currentLevelClicks;

    boolean[] areAnswersCorrect;

    public Level(int level, int score, int amountOfPictures, int currentLevelClicks) {
        this.level = level;
        this.score = score;
        this.currentLevelClicks = currentLevelClicks;
        areAnswersCorrect = new boolean[amountOfPictures];
        preSetAnswerToFalse();
    }

    private void preSetAnswerToFalse() {
        for(int i = 0; i < areAnswersCorrect.length; i++) {
            areAnswersCorrect[i] = false;
        }
    }

    public void updateAnswers(int currentPicturePositionInALevel, boolean wasAnswerCorrect) {
        boolean updated = setNewCorrectAnswer(currentPicturePositionInALevel, wasAnswerCorrect);
        Log.d("LEVELINFOS", " answer was updated in the booleanArray " + updated );
    }

    private boolean setNewCorrectAnswer(int position, boolean answer) {
        if(position == getCurrentLevelClicks()) {
            areAnswersCorrect[position] = answer;
        } else {
            return false;
        }
        return true;
    }

    public boolean[] getAreAnswersCorrect() {
        return areAnswersCorrect;
    }

    public void setAreAnswersCorrect(boolean[] areAnswersCorrect) {
        this.areAnswersCorrect = areAnswersCorrect;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCurrentLevelClicks() {
        return currentLevelClicks;
    }

    public void setCurrentLevelClicks(int currentLevelClicks) {
        this.currentLevelClicks = currentLevelClicks;
    }
}
