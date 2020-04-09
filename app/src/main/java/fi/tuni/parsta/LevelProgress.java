package fi.tuni.parsta;

import android.content.Context;
import android.util.Log;

public class LevelProgress {
    int currentLevel = 1;
    //checked when user clicks the "play" button no main menu and updated always on level progress
    int maxProgressLevel = 1;
    int wantedLevel = 1;

    int currentScore = 0;
    //highest score that player has achieved on current level
    int maxScore = 0;
    int currentLevelClicks = 0;

    boolean[] areAnswersCorrect;
    Context context;

    public LevelProgress(Context context, int currentLevel, int amountOfPictures) {
        this.context = context;
        Log.d("LEVELPROGRESS", currentLevel + " AT CREATION  CURRENT LEVEL IS THIS");
        if(currentLevel != getCurrentLevel()) {
            Log.d("LEVELPROGRESS", currentLevel + " AND GET CURRENT LEVEL WERE NOT THE SAME");
            resetCurrentClicksAndScore();
        }

        setCurrentLevel(currentLevel);
        areAnswersCorrect = UpdateAllLevelAnswers();
    }

    public int getWantedLevel() {
        return wantedLevel;
    }

    public void setWantedLevel(int wantedLevel) {
        this.wantedLevel = wantedLevel;
    }

    public LevelProgress(Context context, int wantedLevel) {
        this.context = context;
        setWantedLevel(wantedLevel);
    }

    private boolean[] UpdateAllLevelAnswers() {
        return ProgressController.getLevelRightAnswerProgress(context, getCurrentLevel());
    }

    public void updateLevelInfo(boolean wasAnswerCorrect, int newScore, int newCurrentClicks) {
        ProgressController.updateLevelProgress(context, currentLevel, newScore, newCurrentClicks, wasAnswerCorrect);
        updateAnswerArray(newCurrentClicks - 1, wasAnswerCorrect);
    }

    private boolean updateAnswerArray(int position, boolean answer) {
        areAnswersCorrect[position] = answer;
        ProgressController.setLevelRightAnswerProgress(context, getCurrentLevel(), position, answer);
        return true;
    }

    public void resetCurrentClicksAndScore() {
        setCurrentLevelClicks(0);
        setCurrentScore(0);
    }

    public void resetCurrentLevelProgressArray(int level) {
        areAnswersCorrect = ProgressController.resetLevelRightAnswerProgress(context, level);
    }

    public boolean[] getAreAnswersCorrect() {
        return areAnswersCorrect;
    }

    public void setAreAnswersCorrect(boolean[] areAnswersCorrect) {
        this.areAnswersCorrect = areAnswersCorrect;
    }

    public int getCurrentLevelClicks() {
        if(this.currentLevelClicks != ProgressController.getLevelCurrentClicks(context, getCurrentLevel())) {
            this.currentLevelClicks = ProgressController.getLevelCurrentClicks(context, getCurrentLevel());
        }
        return this.currentLevelClicks;
    }

    public void setCurrentLevelClicks(int currentLevelClicks) {
        this.currentLevelClicks = currentLevelClicks;
        ProgressController.setLevelCurrentClicks(context, getCurrentLevel(), currentLevelClicks);
    }

    public int getCurrentLevel() {
        if(this.currentLevel != ProgressController.getCurrentLevel(context)) {
            this.currentLevel = ProgressController.getCurrentLevel(context);
        }
        return this.currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
        ProgressController.setCurrentLevelInProgress(context, currentLevel);
    }

    public int getCurrentScore() {
        if(this.currentScore != ProgressController.getLevelCurrentScore(context,currentLevel)) {
            this.currentScore = ProgressController.getLevelCurrentScore(context,currentLevel);
        }
        return this.currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
        ProgressController.setLevelCurrentScore(context, getCurrentLevel(), currentScore);
    }

    public int getMaxScore() {
        return ProgressController.getLevelMaxScore(context, wantedLevel);
    }
}
