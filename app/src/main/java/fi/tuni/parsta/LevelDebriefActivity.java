package fi.tuni.parsta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LevelDebriefActivity extends AppCompatActivity {

    int currentLevel;
    int levelScore;
    TextView congratulations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_debrief);

        congratulations = (TextView) findViewById(R.id.congratulations);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            currentLevel = extras.getInt("currentLevel",0);
            levelScore = extras.getInt("levelScore",0);
            congratulations.setText("Congratulations \n You have passed " + currentLevel + " successfully!" + "\n\n" + "Your score from the level was: " + levelScore);
        }
    }

    public void nextClick(View view) {
        Intent gameIntent = new Intent(this, Game.class);
        gameIntent.putExtra("currentLevel", currentLevel +1);
        startActivity(gameIntent);
    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent(LevelDebriefActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
