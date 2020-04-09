package fi.tuni.parsta;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startClick(View v) {
        Intent gameIntent = new Intent(this, Game.class);
        startActivity(gameIntent);

    }

    public void achivClick(View v) {
        Intent achievementIntent = new Intent(this, AchievementActivity.class);
        startActivity(achievementIntent);

    }

    public void levels(View v) {
        Intent levelIntent = new Intent(this, LevelsActivity.class);
        startActivity(levelIntent);

    }

    public void about(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getResources().getString(R.string.layoutMain_dialog_info1))
                .setTitle(getResources().getString(R.string.layoutMain_dialog_info2));

        AlertDialog dialog = builder.create();

        dialog.show();

        ProgressController.registerAClick(true, this);
    }

    public void settings(View v) {
        SettingsDialog dialog = new SettingsDialog();
        dialog.show(getSupportFragmentManager(), "SettingsDialog");
    }

}
