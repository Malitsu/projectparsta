package fi.tuni.parsta;

import android.os.Bundle;
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

        Achievement testAchv = new Achievement(10);
        testAchv.setUnlockedIcon("unlocked");
        testAchv.setLongDesc("Tunnistit 10 kasvoa oikein");
        achievementsList.add(testAchv);

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
}
