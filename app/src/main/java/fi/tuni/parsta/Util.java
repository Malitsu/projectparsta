package fi.tuni.parsta;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.VIBRATOR_SERVICE;

public class Util {

    public static String readFile(String fileName, Context context) throws IOException {
        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName), "UTF-8"));

        StringBuilder content = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            content.append(line);
        }

        return content.toString();
    }

    public static int getStringResourceByName(String aString, Context context) {
        String packageName = context.getPackageName();
        int resId = context.getResources().getIdentifier(aString, "string", packageName);
        return resId;
    }

    public static int getDrawableResourceByName(String aString, Context context) {
        String packageName = context.getPackageName();
        int resId = context.getResources().getIdentifier(aString, "drawable", packageName);
        return resId;
    }

    public static int random(int min, int max) {
        return (int)(Math.random() * (max + 1 - min)) + min;
    }

    public static boolean stringArrayContains(String[] arr, String targetValue) {
        for(String s: arr){
            if(targetValue.equals(s))
                return true;
        }
        return false;
    }

    public static void playSound(Context context, int audioId) {
        MediaPlayer mp;
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", MODE_PRIVATE);
        boolean playSound = sharedPreferences.getBoolean("sound", true);
        if(playSound) {
            mp = MediaPlayer.create(context, audioId);
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                    mp.release();
                    mp=null;
                }
            });
            mp.start();
        }
    }

    public static void vibrate(View view, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", MODE_PRIVATE);
        boolean vibrate = sharedPreferences.getBoolean("vibration", true);
        Vibrator myVib;

        if(vibrate) {
            Log.d("VIBRATE", "SHOULD BE VIBRATING");
            myVib = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
            myVib.vibrate(50);
//            view.setHapticFeedbackEnabled(true);
//            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        }

    }

    public static void setAppLocale(String localeCode, Context context/*, Activity activity*/) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", MODE_PRIVATE);

        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLocale(new Locale(localeCode.toLowerCase()));
        } else {
            conf.locale = new Locale(localeCode.toLowerCase());
        }
        res.updateConfiguration(conf,dm);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("languageCurrently", localeCode);
        editor.apply();
        Log.d("LANGUAGE", "language should be " + localeCode);
    }
}
