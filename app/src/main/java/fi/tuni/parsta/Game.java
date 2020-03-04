package fi.tuni.parsta;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Game extends AppCompatActivity {

    int rightAnswers = 0;
    int totalAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    protected void gameLoop() {
        while (totalAnswers <= 20) {
            boolean rightanswer = getnewQuestion();
            if(rightanswer) {
                rightAnswers++;
            }
            totalAnswers++;
        }
        endRound();
    }

    protected void endRound() {}

    protected boolean getnewQuestion() {
        return false;
    }



}

