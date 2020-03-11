package fi.tuni.parsta;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;

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

    protected boolean getnewQuestion() {
        return false;
    }



}

