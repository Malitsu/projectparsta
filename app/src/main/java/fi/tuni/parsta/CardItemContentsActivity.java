package fi.tuni.parsta;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;

public class CardItemContentsActivity extends AppCompatActivity {

    private TextView tvDesc;
    private ImageView img;
    String description;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_item_contents);
        tvDesc = (TextView) findViewById(R.id.txtdesc);
        img = (ImageView) findViewById(R.id.itemthumbnail);

        intent = getIntent();
        description = intent.getExtras().getString("fi.tuni.parsta.achievementDesc");

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
}
