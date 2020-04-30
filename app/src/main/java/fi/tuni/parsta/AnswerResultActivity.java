package fi.tuni.parsta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class AnswerResultActivity extends AppCompatActivity {
    TextView pepTalk;
    TextView currentWins;
    String questionImgName;
    String infoQuestionImgName;
    int rightAnswers;
    int clicksNumber;
    int currentLevel;
    boolean playButtonPressed;

    String emotionInfoText;

    ImageView infoImg;
    ImageView answerIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        pepTalk = (TextView) findViewById(R.id.answerText);
        infoImg = (ImageView) findViewById(R.id.answerImg);
        answerIndicator = (ImageView) findViewById(R.id.answerIndicator);
        currentWins = (TextView) findViewById(R.id.currentWins);
        Bundle extras = getIntent().getExtras();
        boolean wasAnswerRight;
        if (extras != null) {
             wasAnswerRight = extras.getBoolean("wasAnswerRight");
             questionImgName = extras.getString("questionImgName");
             rightAnswers = extras.getInt("rightAnswerNumber");
             clicksNumber = extras.getInt("clicksNumber");
             currentLevel = extras.getInt("currentLevel");
             infoQuestionImgName = questionImgName + "_info";
             emotionInfoText = extras.getString("emotionInfoText");
             // TODO: REMOVE THIS ONCE THERE ACTUAL _info IMAGES HAVE BEEN ADDED!
             infoQuestionImgName = "face";

             playButtonPressed = extras.getBoolean("playbuttonpressed");
             int resourceName = Util.getDrawableResourceByName(infoQuestionImgName, this);
             infoImg.setImageResource(resourceName);
             currentWins.setText(getResources().getString(R.string.answerResult_text_correct) + rightAnswers);
            if(wasAnswerRight) {
                pepTalk.setText(getResources().getString(R.string.answerResult_text_pepTalk_correct) + "\n" + emotionInfoText);
                answerIndicator.setImageResource(R.drawable.ic_correct_1);
            } else {
                pepTalk.setText(getResources().getString(R.string.answerResult_text_pepTalk_false) + "\n" + emotionInfoText);
                answerIndicator.setImageResource(R.drawable.ic_wrong_1);
            }
        }
    }

    public void nextClick(View view) {
        Util.vibrate(view, getApplicationContext());
        if(clicksNumber % 10 == 0) {
            Intent gameIntent = new Intent(this, LevelDebriefActivity.class);
            gameIntent.putExtra("currentLevel", currentLevel);
            gameIntent.putExtra("levelScore", rightAnswers);
            gameIntent.putExtra("playbuttonpressed", playButtonPressed);
            startActivity(gameIntent);
        } else {
            Intent gameIntent = new Intent(this, Game.class);
            gameIntent.putExtra("currentLevel", currentLevel);
            gameIntent.putExtra("playbuttonpressed", playButtonPressed);
            startActivity(gameIntent);
        }

    }

    public void quitGame(View v){
        Util.vibrate(v, getApplicationContext());
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent(AnswerResultActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
