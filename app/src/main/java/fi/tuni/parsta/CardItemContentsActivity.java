package fi.tuni.parsta;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;

public class CardItemContentsActivity extends AppCompatActivity {

    private TextView tvDesc;
    private TextView tvDate;
    private ImageView img;
    private String description;
    private Intent intent;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_card_item_contents);
        tvDesc = (TextView) findViewById(R.id.txtdesc);
        img = (ImageView) findViewById(R.id.itemthumbnail);
        tvDate = (TextView) findViewById(R.id.txtdate);

        intent = getIntent();

        int descKey = Util.getStringResourceByName(intent.getExtras().getString("fi.tuni.parsta.achievementDesc"), this);

        description = getString(descKey);
        date = intent.getExtras().getString("fi.tuni.parsta.achievementDate");

        tvDate.setText(date);
        tvDesc.setText(description);
    }

    @Override
    public void onStart() {
        super.onStart();

        String imgString = getIntent().getExtras().getString("fi.tuni.parsta.achievementIcon");
        if(imgString != null){
            int resID = this.getResources().getIdentifier(imgString , "drawable", this.getPackageName());
            img.setImageResource(resID);
        }
    }

    public void quitGameCard(View v){
        Util.vibrate(v, getApplicationContext());
        Intent i= new Intent(this, AchievementActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent(this, AchievementActivity.class);
        startActivity(i);
        finish();
    }
}
