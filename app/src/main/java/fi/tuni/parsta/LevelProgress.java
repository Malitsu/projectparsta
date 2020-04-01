package fi.tuni.parsta;

import android.content.Context;
import android.util.Log;

public class LevelProgress {
    int currentLevel;
    int currentScore;
    //highest score that player has achieved on current level
    int maxScore;
    int currentLevelClicks;

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

    public LevelProgress(int currentLevel, int maxScore) {
        this.currentLevel = currentLevel;
        this.maxScore = maxScore;
    }

    private boolean[] UpdateAllLevelAnswers() {
        Log.d("LEVELPROGRESS", " current level in update answers" + getCurrentLevel());
        return ProgressController.getLevelRightAnswerProgress(context, getCurrentLevel());
    }

    public boolean updateLevelInfo(boolean wasAnswerCorrect, int newScore, int newCurrentClicks) {
        boolean array = updateAnswerArray(newCurrentClicks - 1, wasAnswerCorrect);
        boolean score = updateScore(newScore);
        boolean clicks = updateCurrentClicks(newCurrentClicks);
        if(array && score && clicks) {
            return true;
        } else {
            return false;
        }
    }

    private boolean updateAnswerArray(int position, boolean answer) {
        Log.d("LEVELPROGRESS",position + " UPDATEANSWERARRAY POSITION");
        areAnswersCorrect[position] = answer;
        ProgressController.setLevelRightAnswerProgress(context, getCurrentLevel(), position, answer);
        Log.d("LEVELPROGRESS",  getCurrentLevel() + " answer " + answer + "answer was updated in the booleanArray " + " at position " + (position));
        return true;
    }

    private boolean updateScore(int newScore) {
        setCurrentScore(newScore);
        return true;
    }

    private boolean updateCurrentClicks(int newCurrentClicks) {
        setCurrentLevelClicks(newCurrentClicks);
        return true;
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
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
        ProgressController.setLevelCurrentScore(context, getCurrentLevel(), getCurrentScore());
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
        ProgressController.setLevelMaxScore(context, getCurrentLevel(), getMaxScore());
    }
}
