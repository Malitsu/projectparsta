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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        ArrayList<Achievement> achievements = ProgressController.getAchievements(this);

        RecyclerView myRv = (RecyclerView) findViewById(R.id.recyclerview_id);
        final RecyclerView_Adapter myAdapter = new RecyclerView_Adapter(this, achievements);
        myRv.setLayoutManager(new GridLayoutManager(this, 4));
        myRv.setAdapter(myAdapter);
    }

    public void quitAchievements(View v){
        Util.vibrate(v, getApplicationContext());
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
