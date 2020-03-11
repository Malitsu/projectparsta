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
        List<Achievement> achievementsReached = checkAchievements();
        if (achievementsReached.size() != 0) {
            return generateAchievementToast(achievementsReached, context);
        } else {
            return null;
        }
    }

    private static void initAchievements() {
        achievements = new ArrayList<>();
        for(int n = 0; n < 20; n++) {
            achievements.add(new Achievement(n*10));
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

    private static List<Achievement> checkAchievements() {
        List<Achievement> achievements = getAchievements();
        List<Achievement> achievementsReached = new ArrayList<>();
        for (Achievement achievement : achievements) {
            if (achievement.checkIfCompleted(clicks, wins)) {
                achievement.unlock();
                achievementsReached.add(achievement);
            }
        }
        return achievementsReached;
    }

    public static List<Achievement> getAchievements() {
        if (achievements == null) initAchievements();

        return achievements;
    }
}
