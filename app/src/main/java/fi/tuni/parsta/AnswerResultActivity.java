package fi.tuni.parsta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;


import org.w3c.dom.Text;

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

    String emotionInfo;
    String emotion;

    ImageView infoImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        pepTalk = (TextView) findViewById(R.id.answerText);
        infoImg = (ImageView) findViewById(R.id.answerImg);
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
             emotion = extras.getString("emotion");
             emotionInfo = emotionInfoText(emotion);
             // TODO: REMOVE THIS ONCE THERE ACTUAL _info IMAGES HAVE BEEN ADDED!
             infoQuestionImgName = "face";

             playButtonPressed = extras.getBoolean("playbuttonpressed");
             int resourceName = Util.getStringResourceByName(infoQuestionImgName, this);
             infoImg.setImageResource(resourceName);
             currentWins.setText(getResources().getString(R.string.answerResult_text_correct) + rightAnswers);
            if(wasAnswerRight) {
                pepTalk.setText(getResources().getString(R.string.answerResult_text_pepTalk_correct) + "\n" + emotionInfo);
            } else {
                pepTalk.setText(getResources().getString(R.string.answerResult_text_pepTalk_false) + "\n" + emotionInfo);
            }
        }
    }

    public String emotionInfoText(String emotion) {
        switch(emotion) {
            case "iloinen":
                return getResources().getString(R.string.iloinen);
            case "surullinen":
                return getResources().getString(R.string.surullinen);
            case "pelokas":
                return getResources().getString(R.string.pelokas);
            case "vihainen":
                return getResources().getString(R.string.vihainen);
            case "inhoava":
                return getResources().getString(R.string.inhoava);
            case "halveksuva":
                return getResources().getString(R.string.halveksuva);
            case "yllättynyt":
                return getResources().getString(R.string.yllättynyt);
            default:
                return "Good job";
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
