package fi.tuni.parsta;

import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AchievementActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);
        GridLayout gridLayout = findViewById(R.id.achievement_grid);
        List<Achievement> achievements = ProgressController.getAchievements();

        for (Achievement a : achievements) {
            ImageView image = new ImageView(this);
            image.setImageResource(a.getIcon());
            gridLayout.addView(image);
        }
    }
}
