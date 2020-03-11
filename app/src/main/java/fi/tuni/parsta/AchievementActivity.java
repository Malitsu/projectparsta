package fi.tuni.parsta;

import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AchievementActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);
        GridLayout gridLayout = findViewById(R.id.achievement_grid);
        for (int n = 0; n < 25; n++) {
            int random = (int)(Math.random()*2);
                ImageView image = new ImageView(this);
            if (random == 0) {
                image.setImageResource(R.drawable.star);
            } else {
                image.setImageResource(R.drawable.question);
            }
            gridLayout.addView(image);
        }
    }
}
