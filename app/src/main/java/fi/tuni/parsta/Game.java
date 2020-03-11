package fi.tuni.parsta;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Game extends AppCompatActivity {

    int rightAnswers = 0;
    int totalAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

//        String resourceId = "@drawable/happy_placeholder";
//
//        TypedValue value = new TypedValue();
//        this.getResources().getValue(resourceId, value, true);

        Field[] fields = R.drawable.class.getFields();
        List<Integer> amountOfAllImages = new ArrayList<Integer>();
        for (Field field : fields) {
            // Take only those with name starting with "fr"
            Log.d("GameClass", field.getName());
            if (field.getName().contains("face_")) {
                try {
                    amountOfAllImages.add(field.getInt(null));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d("GameClass", amountOfAllImages.size() + " ");
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

