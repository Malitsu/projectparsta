package fi.tuni.facingo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    boolean language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("settings",MODE_PRIVATE);

        //Set language of the app to phone default on first load of the application and save the info to preferences
        if(sharedPreferences.getString("languageCurrently","").equals("")) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if(Locale.getDefault().toString().equals("fi_FI") || Locale.getDefault().toString().contains("fi")) {
                editor.putString("languageCurrently", "fi");
            } else {
                editor.putString("languageCurrently", "en");
            }
            editor.apply();
        }

        Locale locale = new Locale(sharedPreferences.getString("languageCurrently", ""));

        Configuration config = new Configuration();
        config.locale = locale;

        getResources().updateConfiguration(
                config,
                getResources().getDisplayMetrics()
        );

        setContentView(R.layout.activity_main);
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
    }

    public void settings(View v) {
        Util.vibrate(v, getApplicationContext());
        SettingsDialog dialog = new SettingsDialog();
        dialog.show(getSupportFragmentManager(), "SettingsDialog");
    }
}
