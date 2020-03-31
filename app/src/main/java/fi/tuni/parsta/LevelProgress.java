package fi.tuni.parsta;

import android.util.Log;

public class LevelProgress {
    int level;
    int score;
    int currentLevelClicks;

    boolean[] areAnswersCorrect;

    public LevelProgress(int level, int score, int amountOfPictures, int currentLevelClicks) {
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
        if(newScore > getScore()) {
            setScore(newScore);
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
        setScore(0);
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

}
