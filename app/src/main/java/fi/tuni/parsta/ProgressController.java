package fi.tuni.parsta;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ProgressController {
    private static List<Achievement> achievements;
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
        Achievement achievement = checkAchievements();
        if (achievement != null) {
            return generateAchievementToast(new Achievement(), context);
        } else {
            return null;
        }
    }

    private static void initAchievements() {
        achievements = new ArrayList<>();
        achievements.add(new Achievement());
    }

    // TODO: Allow possibility of displaying multiple achievements at once?
    private static Toast generateAchievementToast(Achievement achievement, Context context) {
        CharSequence text = achievement.getShortDesc();
        int duration = Toast.LENGTH_LONG;

        return Toast.makeText(context, text, duration);
    }

    private static Achievement checkAchievements() {
        if (achievements == null) initAchievements();
        for (Achievement achievement : achievements) {
            if (!achievement.isUnlocked()) {
                if (clicks > achievement.getClicksRequired()) {
                    achievement.lock();
                    return achievement;
                }
            }
        }
        return null;
    }
}

class Achievement {
    private boolean unlocked;
    private String longDesc;
    private String shortDesc = "This is a placeholder text";
    private int clicksRequired = 10;

    public String getShortDesc() {
        return shortDesc;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void lock() {
        unlocked = false;
    }

    public int getClicksRequired() {
        return clicksRequired;
    }
}
