package fi.tuni.parsta;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;

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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Game extends AppCompatActivity {

    int rightAnswers = 0;
    int totalAnswers = 0;
    private Gson gson;
    GameImage[] images;
    int level = 1;
    ImageView questionImg;

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
        gameLoop();
    }

    protected void gameLoop() {
        Boolean rightAnswer = false;

        //Get random question image (new question)
        int rndImage = (int) (Math.random() * images.length);
        GameImage newQuestion = images[rndImage];
        String questionImgName = newQuestion.getName();
        String rightAnswerString = questionImgName.substring(4);
        Log.d("GAMEIMAGE", rightAnswerString);

        //Set image to image view
        int resourceName = getStringResourceByName(questionImgName);

        questionImg = (ImageView) findViewById(R.id.questionImg);
        questionImg.setImageResource(resourceName);

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

    private int getStringResourceByName(String aString) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(aString, "drawable", packageName);
        return resId;
    }

    public ArrayList<String> getAnswerOptions(){
        return new ArrayList<>();
    }

}

