package fi.tuni.parsta;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ProgressController {
    private static Achievement[] achievements;
    private static int clicks;
    private static int wins;

    public static Toast registerAClick(boolean beatLevel, int levelID, boolean win, Context context) {
        // TODO: Do something with the level info
        return registerAClick(win, context);
    }

    public static Toast registerAClick(boolean win, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("progress", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        clicks = sharedPreferences.getInt("clicks", 0);
        wins = sharedPreferences.getInt("wins", 0);
        clicks++;
        if (win) wins++;
        editor.putInt("clicks", clicks);
        editor.putInt("wins", wins);
        editor.apply();
        List<Achievement> achievementsReached = checkAchievements(context);
        if (achievementsReached.size() != 0) {
            return generateAchievementToast(achievementsReached, context);
        } else {
            return null;
        }
    }

    private static void initAchievements(Context context) {
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            String data = Util.readFile("achievements.json", context);
            Log.d("GAMEHELLO", data);
            achievements = gson.fromJson(data, Achievement[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: Allow possibility of displaying multiple achievements at once?
    private static Toast generateAchievementToast(List<Achievement> achievementsReached,
                                                  Context context) {
        Achievement achievement = achievementsReached.get(0);
        CharSequence text = achievement.getShortDesc();
        int duration = Toast.LENGTH_LONG;
        return Toast.makeText(context, text, duration);
    }

    private static List<Achievement> checkAchievements(Context context) {
        Achievement[] achievements = getAchievements(context);
        List<Achievement> achievementsReached = new ArrayList<>();
        for (Achievement achievement : achievements) {
            if (achievement.checkIfCompleted(clicks, wins)) {
                achievement.unlock();
                achievementsReached.add(achievement);
            }
        }
        return achievementsReached;
    }

    public static Achievement[] getAchievements(Context context) {
        if (achievements == null) initAchievements(context);
        return achievements;
    }
}
