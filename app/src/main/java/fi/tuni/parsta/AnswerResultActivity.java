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
        Intent gameIntent = new Intent(this, Game.class);
        startActivity(gameIntent);
    }
}
