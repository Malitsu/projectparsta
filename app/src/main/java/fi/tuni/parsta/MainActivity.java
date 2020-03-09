package fi.tuni.parsta;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
        Intent achievementIntent = new Intent(this, Achievement.class);
        startActivity(achievementIntent);

    }

    public void about(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Tämä on kasvojen tunnistus peli.\nHanke:\nTekijät:")
                .setTitle("Kasvojen tunnistus peli");

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    public void settings(View v) {
        SettingsDialog dialog = new SettingsDialog();
        dialog.show(getSupportFragmentManager(), "SettingsDialog");
    }

}
