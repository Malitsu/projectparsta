package fi.tuni.parsta;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    boolean language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("settings",MODE_PRIVATE);
        Log.d("LANGUAGE", sharedPreferences.getString("languageCurrently", ""));
        Util.setAppLocale(sharedPreferences.getString("languageCurrently", ""), getApplicationContext());
    }

    public void startClick(View v) {
        Util.vibrate(v, getApplicationContext());
        Intent gameIntent = new Intent(this, Game.class);
        gameIntent.putExtra("playbuttonpressed", true);
        startActivity(gameIntent);
    }

    public void achivClick(View v) {
        Util.vibrate(v, getApplicationContext());
        Intent achievementIntent = new Intent(this, AchievementActivity.class);
        startActivity(achievementIntent);

    }

    public void levels(View v) {
        Util.vibrate(v, getApplicationContext());
        Intent levelIntent = new Intent(this, LevelsActivity.class);
        startActivity(levelIntent);

    }

    public void about(View v) {
        Util.vibrate(v, getApplicationContext());
        AboutDialog dialog = new AboutDialog();
        dialog.show(getSupportFragmentManager(), "aboutDialog");

        ProgressController.registerAClick(true, this);
    }

    public void settings(View v) {
        Util.vibrate(v, getApplicationContext());
        SettingsDialog dialog = new SettingsDialog();
        dialog.show(getSupportFragmentManager(), "SettingsDialog");
    }
}
