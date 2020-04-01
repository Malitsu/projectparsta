package fi.tuni.parsta;

import android.util.Log;

public class LevelProgress {
    int currentLevel;
    int currentScore;
    //highest score that player has achieved on current level
    int maxScore;
    int currentLevelClicks;

    boolean[] areAnswersCorrect;

    public LevelProgress(int currentLevel, int currentScore, int amountOfPictures, int currentLevelClicks) {
        this.currentLevel = currentLevel;
        this.currentScore = currentScore;
        this.currentLevelClicks = currentLevelClicks;
        areAnswersCorrect = new boolean[amountOfPictures];
        preSetAnswerToFalse();
    }

    public LevelProgress(int currentLevel, int maxScore) {
        this.currentLevel = currentLevel;
        this.maxScore = maxScore;
    }

    private void preSetAnswerToFalse() {
        for(int i = 0; i < areAnswersCorrect.length; i++) {
            areAnswersCorrect[i] = false;
        }
    }

    public boolean updateLevelInfo(int currentPictureNumberInALevel, boolean wasAnswerCorrect, int newScore, int newCurrentClicks) {
        boolean array = updateAnswerArray(currentPictureNumberInALevel,wasAnswerCorrect);
        boolean score = updateScore(newScore);
        boolean clicks = updateCurrentClicks(newCurrentClicks);
        if(array && score && clicks) {
            return true;
        } else {
            return false;
        }
    }

    private boolean updateAnswerArray(int position, boolean answer) {
        boolean updated;
        if(position == getCurrentLevelClicks()) {
            updated = areAnswersCorrect[position] = answer;
        } else {
            return false;
        }
        Log.d("LEVELINFOS", " answer was updated in the booleanArray " + updated  + " at position" + position);
        return true;
    }

    private boolean updateScore(int newScore) {
        if(newScore > getCurrentScore()) {
            setCurrentScore(newScore);
            return true;
        } else {
            return false;
        }
    }

    private boolean updateCurrentClicks(int newCurrentClicks) {
        if(newCurrentClicks > getCurrentLevelClicks()) {
            setCurrentLevelClicks(newCurrentClicks);
            return true;
        } else {
            return false;
        }
    }

    public void resetCurrentClicksAndScore() {
        setCurrentLevelClicks(0);
        setCurrentScore(0);
    }

    public boolean[] getAreAnswersCorrect() {
        return areAnswersCorrect;
    }

    public void setAreAnswersCorrect(boolean[] areAnswersCorrect) {
        this.areAnswersCorrect = areAnswersCorrect;
    }

    public int getCurrentLevelClicks() {
        return currentLevelClicks;
    }

    public void setCurrentLevelClicks(int currentLevelClicks) {
        this.currentLevelClicks = currentLevelClicks;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }
}
