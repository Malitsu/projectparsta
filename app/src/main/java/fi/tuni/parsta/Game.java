package fi.tuni.parsta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    LinearLayout buttonLayout;
//    TextView currentWins;
    TextView levelDisplayGame;
    //Temporary
    Boolean firstRound = true;
    ArrayList<Button> buttonList = new ArrayList<>();
    int rightAnswersInt = 0;
    int clicks = 0;
    String rightAnswerString;
    SharedPreferences sharedPreferences;
    String questionImgName;
    List<Dots> listDots;

    LevelProgress levelProgress;
    int amountOfPicturesInALevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        setAmountOfPicturesInALevel(10);

        level = ProgressController.getCurrentLevel(this);
        if(level == 0) {
            level = 1;
        }
        //Creates a new level progress object based on the currentLevel saved in the progress controller
        //and the amountOfPictures that the level should have (at the moment always 10)
        levelProgress = new LevelProgress(this, level, getAmountOfPicturesInALevel());
        clicks = levelProgress.getCurrentLevelClicks();

        listDots = new ArrayList<>();

        //Update the dots in the progress bar at the start of the game
        addDots();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        convertJsonToGameImageObjects();
        for(GameImage i : images) {
            Log.d("GAMEIMAGETAG",i.name);
        }

//        currentWins = (TextView) findViewById(R.id.currentWins);
        levelDisplayGame = (TextView) findViewById(R.id.levelDisplayGame);
        sharedPreferences = getSharedPreferences("progress", MODE_PRIVATE);
        clicks = levelProgress.getCurrentLevelClicks();

        rightAnswersInt = levelProgress.getCurrentScore();

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            level = extras.getInt("currentLevel",1);
        }
        levelDisplayGame.setText("Taso: " + level);
//        currentWins.setText("Oikein: " + rightAnswersInt);
        gameLoop();

    }

    public void addDots() {
            //Update dots in the progress bar based on current level progress
            boolean[] answers = levelProgress.getAreAnswersCorrect();
            int currentClicks = levelProgress.getCurrentLevelClicks();
            for(int i = 0; i < answers.length; i++) {
//                Log.d("LEVELPROGRESS", answers[i] + " was found at " + i);
                if( i < currentClicks) {
                    if(answers[i] == true) {
                         listDots.add(new Dots(R.drawable.ic_dot_green));
                    } else {
                        listDots.add(new Dots(R.drawable.ic_dot_red));
                    }
                } else if( i == currentClicks) {
                    listDots.add(new Dots(R.drawable.ic_dot_current));
                } else {
                    listDots.add(new Dots(R.drawable.ic_dot_white));
                }
            }


        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_gameLoop);
        DotRecyclerView_Adapter myAdapter = new DotRecyclerView_Adapter(this, listDots);
        myrv.setLayoutManager(new GridLayoutManager(this,10));
        myrv.setAdapter(myAdapter);
    }


    protected void gameLoop() {
        Boolean rightAnswer = false;

        //Get random question image (new question)
        int rndImage = Util.random(0, (images.length - 1));
        GameImage newQuestion = images[rndImage];
        questionImgName = newQuestion.getName();

        //Edit questionImgName string (for answer button)
        rightAnswerString = questionImgName.substring(5);
        int firstUnderscorePosition = rightAnswerString.indexOf('_');
        rightAnswerString = rightAnswerString.substring(0, firstUnderscorePosition);
        rightAnswerString = rightAnswerString.toLowerCase();
        Log.d("GAMEIMAGE", rightAnswerString);

        //Set image to image view
        int resourceName = Util.getStringResourceByName(questionImgName, this);

        questionImg = (ImageView) findViewById(R.id.questionImg);
        questionImg.setImageResource(resourceName);

        //Get answer options
        ArrayList<String> answerOptions = getAnswerOptions(getAnswerOptionsAmount(level), rightAnswerString);
        Log.d("JEE", answerOptions.toString());

        //Create buttons with answer options
        buttonLayout = findViewById(R.id.button_grid);

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
            String jsonFileContent = Util.readFile("faces.json",this);
            Log.d("GAMEHELLO", jsonFileContent);
            images = gson.fromJson(jsonFileContent, GameImage[].class);

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
                    String potetialAnswerOption = getRandomAnswerOption().toLowerCase();
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
            myButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            myButton.setWidth(1000);

            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    gameLoop();
                    Intent gameIntent = new Intent(getApplication(), AnswerResultActivity.class);
                    clicks++;
                    if(rightAnswerString.contains(myButton.getText().toString())) {
                        rightAnswersInt++;
                        Toast toast =ProgressController.registerAClick(true, getApplicationContext());
                        if (toast != null) toast.show();
                        Util.playSound(getApplication(), R.raw.right);
                        levelProgress.updateLevelInfo(true, rightAnswersInt, clicks);
                        gameIntent.putExtra("wasAnswerRight",true);
                    } else {
                        Toast toast = ProgressController.registerAClick(false, getApplicationContext());
                        if (toast != null) toast.show();
                        Util.playSound(getApplication(), R.raw.wrong);
                        levelProgress.updateLevelInfo(false, rightAnswersInt, clicks);
                        gameIntent.putExtra("wasAnswerRight",false);

                    }
                    gameIntent.putExtra("questionImgName", questionImgName);
                    gameIntent.putExtra("rightAnswerNumber", rightAnswersInt);
                    gameIntent.putExtra("clicksNumber", clicks);
                    gameIntent.putExtra("currentLevel", level);
                    startActivity(gameIntent);
                }
            });
            myButton.setText(answerOptions.get(i));
            buttonList.add(myButton);
            buttonLayout.addView(myButton);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 15);
            myButton.setLayoutParams(params);
        }
    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent(Game.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    public void quitGame(View v){
        Intent mainIntent = new Intent(getApplication(), MainActivity.class);
        startActivity(mainIntent);
    }

    public int getAmountOfPicturesInALevel() {
        return amountOfPicturesInALevel;
    }

    public void setAmountOfPicturesInALevel(int amountOfPicturesInALevel) {
        this.amountOfPicturesInALevel = amountOfPicturesInALevel;
    }


}

