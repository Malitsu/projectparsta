package fi.tuni.parsta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Game extends AppCompatActivity {

    int rightAnswers = 0;
    int totalAnswers = 0;
    private Gson gson;
    GameImage[] images;
    int level = 1;
    ImageView questionImg;
    GridLayout gridLayout;
    TextView currentWins;
    //Temporary
    Boolean firstRound = true;
    ArrayList<Button> buttonList = new ArrayList<>();
    int rightAnswersInt = 0;
    int clicks = 0;
    String rightAnswerString;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        convertJsonToGameImageObjects();
        for(GameImage i : images) {
            Log.d("GAMEIMAGETAG",i.name);
        }

        currentWins = (TextView) findViewById(R.id.currentWins);
        sharedPreferences = getSharedPreferences("progress", MODE_PRIVATE);
        clicks = sharedPreferences.getInt("clicks", 0);
        rightAnswersInt = sharedPreferences.getInt("wins", 0);
        currentWins.setText("Oikein: " + rightAnswersInt);
        gameLoop();


    }

    protected void gameLoop() {
        Boolean rightAnswer = false;

        //Get random question image (new question)
        int rndImage = Util.random(0, (images.length - 1));
        GameImage newQuestion = images[rndImage];
        String questionImgName = newQuestion.getName();

        //Edit questionImgName string (for answer button)
        rightAnswerString = questionImgName.substring(5);
        int firstUnderscorePosition = rightAnswerString.indexOf('_');
        rightAnswerString = rightAnswerString.substring(0, firstUnderscorePosition);
        Log.d("GAMEIMAGE", String.valueOf(firstUnderscorePosition));


        Log.d("GAMEIMAGE", rightAnswerString);

        //Set image to image view
        int resourceName = Util.getStringResourceByName(questionImgName, this);

        questionImg = (ImageView) findViewById(R.id.questionImg);
        questionImg.setImageResource(resourceName);

        //Get answer options
        ArrayList<String> answerOptions = getAnswerOptions(getAnswerOptionsAmount(level), rightAnswerString);
        Log.d("JEE", answerOptions.toString());

        //Create buttons with answer options
        gridLayout = findViewById(R.id.button_grid);

        //Temporary
        if(firstRound){
            createButtonGrid(answerOptions);
            firstRound = false;
        }else{
            for(int i=0; i<answerOptions.size();i++){
                buttonList.get(i).setText(answerOptions.get(i));
            }
        }


//        if(questionImgName.contains(****pressedButtonText****)){
//            rightAnswer = true;
//        }
    }

    public  void  convertJsonToGameImageObjects() {
        try {
            String hellou = Util.readFile("faces.json",this);
            Log.d("GAMEHELLO",hellou);
            images = gson.fromJson(hellou, GameImage[].class);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void endRound() {}

    public ArrayList<String> getAnswerOptions(int answerOptionsAmount, String rightAnswer){
        String[] answerOptions = new String[answerOptionsAmount];
        int rightAnswerposition = Util.random(0, (answerOptionsAmount - 1));

        answerOptions[rightAnswerposition] = rightAnswer;
        answerOptions = addRandomAnswerOptionsToArray(answerOptions);

        //Convert array to arrayList
        ArrayList<String> returnable = new ArrayList<>();
        Collections.addAll(returnable, answerOptions);
        return returnable;
    }

    public String[] addRandomAnswerOptionsToArray(String[] array){
        for(int i=0; i<array.length; i++){
            if(array[i] == null){
                Boolean ok = false;
                while(!ok){
                    String potetialAnswerOption = getRandomAnswerOption();
                    if(!Util.stringArrayContains(array, potetialAnswerOption)){
                        array[i] = potetialAnswerOption;
                        ok = true;
                    }
                }
            }
        }
        return array;
    }

    public String getRandomAnswerOption(){
        Resources res = getResources();
        String[] expressions = res.getStringArray(R.array.facial_expressions_array);
        String returnable = expressions[Util.random(0, (expressions.length - 1))];
        return returnable;
    }

    public int getAnswerOptionsAmount(int level){
        return 3 + level - 1;
    }

    public void createButtonGrid(ArrayList<String> answerOptions){
        for (int i = 0; i<answerOptions.size(); i++) {
            final Button myButton = new Button(this);
            myButton.setWidth(1000);

            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    gameLoop();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if(rightAnswerString.contains(myButton.getText().toString())) {
                        rightAnswersInt++;
                        clicks++;
                        editor.putInt("clicks", clicks);
                        editor.putInt("wins", rightAnswersInt);
                        editor.apply();
                        currentWins.setText("Oikein: " + rightAnswersInt);
                        Intent gameIntent = new Intent(getApplication(), AnswerResultActivity.class);
                        gameIntent.putExtra("wasAnswerRight",true);
                        startActivity(gameIntent);
                    } else {
                        clicks++;
                        editor.putInt("clicks", clicks);
                        editor.apply();
                        Intent gameIntent = new Intent(getApplication(), AnswerResultActivity.class);
                        gameIntent.putExtra("wasAnswerRight",false);
                        startActivity(gameIntent);
                    }
                }
            });
            myButton.setText(answerOptions.get(i));
            buttonList.add(myButton);
            gridLayout.addView(myButton);
        }
    }

}

