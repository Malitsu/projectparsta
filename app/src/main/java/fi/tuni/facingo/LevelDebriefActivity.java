package fi.tuni.facingo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LevelDebriefActivity extends AppCompatActivity {

    int currentLevel;
    int levelScore;
    TextView congratulations;
    boolean playButtonPressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_level_debrief);

        congratulations = (TextView) findViewById(R.id.congratulations);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            currentLevel = extras.getInt("currentLevel",0);
            levelScore = extras.getInt("levelScore",0);
            playButtonPressed = extras.getBoolean("playbuttonpressed");
            String feedback = "";
            if(levelScore <= 4){
                // TODO: Create new button to take the player back to the start of the level is score low enough
                feedback = getResources().getString(R.string.levelDebrief_text_levelPassed_lowerScore);
            }else if(levelScore <= 8){
                feedback = getResources().getString(R.string.levelDebrief_text_levelPassed_middleScore);
            }else{
                feedback = getResources().getString(R.string.levelDebrief_text_levelPassed_higherScore);
            }

            congratulations.setText(getResources().getString(R.string.layoutLevels_text_points) + levelScore + "\n\n" +
                    feedback);
        }
        currentLevel++;
        ProgressController.setCurrentLevelInProgress(getApplicationContext(), currentLevel);
        ProgressController.setMaxProgressLevel(getApplicationContext(), currentLevel);
    }

    public void quitGame(View v){
        Util.vibrate(v, getApplicationContext());
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }

    public void nextClick(View view) {
        Util.vibrate(view, getApplicationContext());
        Intent gameIntent = new Intent(this, Game.class);
        Intent mainIntent = new Intent(this, MainActivity.class);
        gameIntent.putExtra("currentLevel", currentLevel);
        gameIntent.putExtra("playbuttonpressed", playButtonPressed);
        gameIntent.putExtra("shouldresetcurrentprogress", true);
        if(currentLevel > 10) {
            startActivity(mainIntent);
        } else {
            startActivity(gameIntent);
        }

    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent(LevelDebriefActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
