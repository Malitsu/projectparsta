package fi.tuni.parsta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game extends AppCompatActivity {

    boolean wasAnswerRight;
    private Gson gson;
    ArrayList<GameImage> images;
    int currentLevel = 1;
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
    SharedPreferences settingsPreferences;
    String questionImgName;
    List<Dots> listDots;

    LevelProgress levelProgress;

    Level level;

    private final int NUMBEROFLEVELSINGAME = 10;
    private final int PICTURESPERLEVEL = 10;

    boolean finishedGame = false;
    boolean playButtonClicked;
    boolean pleaseResetProgress;

    String languageCurrently;
    String emotionInfoText;

    GameImage newQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle extras = getIntent().getExtras();
        settingsPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        languageCurrently = settingsPreferences.getString("languageCurrently", "");
        //TODO: reset the progress of a level if gotten through the level menu and the level is bigger than the one that we got through the menu?

        // check the wanted, current level based on intent info from other activities
        if (extras != null) {
            // gets carried through the game(answer results and level debrief), first set at play button or levels activity
            playButtonClicked = extras.getBoolean("playbuttonpressed");
            if(playButtonClicked) {
                // resets the current level to the max progress level if it has been something else at some point.
                currentLevel = ProgressController.getMaxProgressLevel(this);
            } else {
                // get's the current level wanted, updated via intent from everywhere
                currentLevel = extras.getInt("currentLevel", 1);
            }
            Log.d("LEVELPROGRESS", currentLevel + " and " + ProgressController.getCurrentLevel(this ) + " resetttttt should hapen");
            // reset the progress of a level if the level is clicked and it's new and it's different one from before
            // don't reset if the level is max progress level or if the level is the one last played
            if(currentLevel != ProgressController.getCurrentLevel(this)
                    && currentLevel != ProgressController.getMaxProgressLevel(this)
                    || extras.getBoolean("shouldresetcurrentprogress")) {
                pleaseResetProgress = true;
                Log.d("LEVELPROGRESS", pleaseResetProgress + " resetttttt should hapen");
            }
            ProgressController.setCurrentLevelInProgress(this, currentLevel);
        }

        //if level is 0 for some reason, reset it to 1 to prevent problems.
        if(currentLevel < 1) {
            currentLevel = 1;
        }

        //if the game has been finished (max level is above 10), reset the game to play level 10 again
        if(currentLevel > NUMBEROFLEVELSINGAME) {
            currentLevel = NUMBEROFLEVELSINGAME;
            finishedGame = true;
        }
        //Creates a new level progress object based on the currentLevel saved in the progress controller
        //and the amountOfPictures that the level should have (at the moment always 10)
        levelProgress = new LevelProgress(this, currentLevel, PICTURESPERLEVEL);

        // if the game has been finished or a level is being replayed with it finished before
        // reset the clicks and progress array for the round
        if(finishedGame || (levelProgress.getCurrentLevelClicks() >= PICTURESPERLEVEL) || pleaseResetProgress){
            Log.d("LEVELPROGRESS", pleaseResetProgress + " resetttttt should hapen");
            levelProgress.resetCurrentClicksAndScore();
            levelProgress.resetCurrentLevelProgressArray(currentLevel);
        }
        clicks = levelProgress.getCurrentLevelClicks();
        rightAnswersInt = levelProgress.getCurrentScore();

        // used to create the dots that are in the progress bar
        listDots = new ArrayList<>();

        //Update the dots in the progress bar at the start of the game
        addDots();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        convertJsonToGameImageObjects();
        for(GameImage i : images) {
            Log.d("GAMEIMAGETAG",i.getName());
        }

        levelDisplayGame = (TextView) findViewById(R.id.levelDisplayGame);
        sharedPreferences = getSharedPreferences("progress", MODE_PRIVATE);

        levelDisplayGame.setText(getResources().getString(R.string.game_text_level) + currentLevel);
        gameLoop();

    }

    public void addDots() {
            //Update dots in the progress bar based on current level progress
            boolean[] answers = levelProgress.getAreAnswersCorrect();
            int currentClicks = levelProgress.getCurrentLevelClicks();
            for(int i = 0; i < answers.length; i++) {
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


    private int lastImage = -1;

    protected void gameLoop() {
        Boolean rightAnswer = false;

        //Get random question image (new question)

        int rndImage = -1;
        boolean okRandomImage = false;

        while (!okRandomImage) {
            rndImage = Util.random(0, (images.size() - 1));
            if (rndImage != lastImage) okRandomImage = true;
        }
        lastImage = rndImage;

        newQuestion = images.get(rndImage);
        questionImgName = newQuestion.getName();

        if(languageCurrently.equals("fi")) {
            rightAnswerString = newQuestion.getEmotionFi();
            emotionInfoText = newQuestion.getEmotionInfoFi();
        } else {
            rightAnswerString = newQuestion.getEmotionEn();
            emotionInfoText = newQuestion.getEmotionInfoEn();
        }


        //Set image to image view
        int resourceName = Util.getDrawableResourceByName(questionImgName, this);

        questionImg = (ImageView) findViewById(R.id.questionImg);
        questionImg.setImageResource(resourceName);

        //Get answer options
        ArrayList<String> answerOptions = getAnswerOptions(level.getChoices(), rightAnswerString);
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
    }

    public  void  convertJsonToGameImageObjects() {
        images = new ArrayList<>();
        try {
            String jsonFileContent = Util.readFile("faces.json",this);
            GameImage[] tempImages = gson.fromJson(jsonFileContent, GameImage[].class);

            jsonFileContent = Util.readFile("levels.json", this);
            Level[] levels = gson.fromJson(jsonFileContent, Level[].class);

            for (Level value : levels) {
                if (value.getId() == currentLevel) {
                    level = value;
                }
            }

            for (int i = 0; i < level.getFaces().length; i++) {
                for (GameImage tempImage : tempImages) {
                    if (tempImage.getId() == level.getFaces()[i]) {
                        images.add(tempImage);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getAnswerOptions(int answerOptionsAmount, String rightAnswer){
        System.out.println(level);

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
        String[] expressions;

        if(languageCurrently.equals("fi")) {
            expressions = res.getStringArray(R.array.facial_expressions_arrayFi);
        } else {
            expressions = res.getStringArray(R.array.facial_expressions_arrayEn);
        }

        String returnable = expressions[Util.random(0, (expressions.length - 1))];
        return returnable;
    }

    public void createButtonGrid(ArrayList<String> answerOptions){
        for (int i = 0; i<answerOptions.size(); i++) {
            final Button myButton = new Button(this);
            myButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            myButton.setWidth(1000);

            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Util.vibrate(view, getApplicationContext());
                    Intent gameIntent = new Intent(getApplication(), AnswerResultActivity.class);
                    clicks++;
                    wasAnswerRight = rightAnswerString.contains(myButton.getText().toString());
                    if(wasAnswerRight) {
                        rightAnswersInt++;
                        Util.playSound(getApplication(), R.raw.right);
                    } else {
                        Util.playSound(getApplication(), R.raw.wrong);
                    }
                    ProgressController.registerAClick(wasAnswerRight, getApplicationContext());
                    levelProgress.updateLevelInfo( wasAnswerRight, rightAnswersInt, clicks);
                    gameIntent.putExtra("wasAnswerRight",wasAnswerRight);
                    gameIntent.putExtra("questionImgName", questionImgName);
                    gameIntent.putExtra("rightAnswerNumber", rightAnswersInt);
                    gameIntent.putExtra("clicksNumber", clicks);
                    gameIntent.putExtra("currentLevel", currentLevel);
                    gameIntent.putExtra("playbuttonpressed", playButtonClicked);
                    gameIntent.putExtra("emotionInfoText", emotionInfoText);


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
            params.setMargins(0, 2, 0, 10);
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
        Util.vibrate(v, getApplicationContext());
        Intent mainIntent = new Intent(getApplication(), MainActivity.class);
        startActivity(mainIntent);
    }

}

