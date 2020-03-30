package fi.tuni.parsta;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AchievementActivity extends AppCompatActivity {
    List<Achievement> achievementsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);
        achievementsList = new ArrayList<Achievement>();


        //*********************************************************************
        // Achievements for testing purpose
        Achievement testAchv = new Achievement(10);
        testAchv.setUnlockedIcon("unlocked");
        testAchv.setLongDesc("Tunnistit 10 kasvoa oikein");
        testAchv.unlock();
        achievementsList.add(testAchv);

        Achievement testAchv2 = new Achievement(10);
        testAchv2.setUnlockedIcon("unlocked");
        testAchv2.setLongDesc("Tunnistit 1 000 000 kasvoa oikein");
        testAchv2.unlock();
        achievementsList.add(testAchv2);

        for(int i=0; i<30;i++){
            Achievement testAchv3 = new Achievement(10);
            testAchv3.setUnlockedIcon("unlocked");
            testAchv3.setLongDesc("Tunnistit 1 000 000 kasvoa oikein");
            achievementsList.add(testAchv3);
        }

        //**********************************************************************

//        GridLayout gridLayout = findViewById(R.id.achievement_grid);
//        Achievement[] achievements = ProgressController.getAchievements(this);
//
//        for (Achievement a : achievements) {
//            ImageView image = new ImageView(this);
//            image.setImageResource(Util.getStringResourceByName(a.getIcon(), this));
//            gridLayout.addView(image);
//        }


        RecyclerView myRv = (RecyclerView) findViewById(R.id.recyclerview_id);
        final RecyclerView_Adapter myAdapter = new RecyclerView_Adapter(this, achievementsList);
        myRv.setLayoutManager(new GridLayoutManager(this, 4));
        myRv.setAdapter(myAdapter);
    }

    public void quitAchievements(View v){
        Intent i= new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
