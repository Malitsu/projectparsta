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
             int resourceName = Util.getStringResourceByName(infoQuestionImgName, this);
             infoImg.setImageResource(resourceName);
             currentWins.setText("Oikein: " + rightAnswers);

            if(wasAnswerRight) {
                pepTalk.setText("Vastasit oikein kysymykseen! \n" +
                        "Yläpuolen kuvasta näet tietoa mihin keskittyä ilmeen tunnistamisessa.");
            } else {
                pepTalk.setText("Parempi onni ensi kerralla. \n" +
                        "Yläpuolen kuvasta näet tietoa mihin keskittyä ilmeen tunnistamisessa.");
            }
        }
    }

    public void nextClick(View view) {
        if(clicksNumber % 10 == 0) {
            Intent gameIntent = new Intent(this, LevelDebriefActivity.class);
            gameIntent.putExtra("currentLevel", currentLevel);
            gameIntent.putExtra("levelScore", rightAnswers);
            startActivity(gameIntent);
        } else {
            Intent gameIntent = new Intent(this, Game.class);
            gameIntent.putExtra("currentLevel", currentLevel);
            startActivity(gameIntent);
        }

    }

    public void quitGame(View v){
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
