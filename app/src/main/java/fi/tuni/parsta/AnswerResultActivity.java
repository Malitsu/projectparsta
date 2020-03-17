package fi.tuni.parsta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class AnswerResultActivity extends AppCompatActivity {
    TextView pepTalk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        pepTalk = (TextView) findViewById(R.id.answerText);
        Bundle extras = getIntent().getExtras();
        boolean wasAnswerRight;
        if (extras != null) {
             wasAnswerRight = extras.getBoolean("wasAnswerRight");
            if(wasAnswerRight) {
                pepTalk.setText("v jes hyvä sinä");
            } else {
                pepTalk.setText("parempi onni ensi kerralla (:");
            }
        }



    }

    public void nextClick(View view) {
        Intent gameIntent = new Intent(this, Game.class);
        startActivity(gameIntent);
    }
}
