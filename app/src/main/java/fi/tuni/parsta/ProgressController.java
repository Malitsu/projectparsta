package fi.tuni.parsta;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ProgressController {
    private static Achievement[] achievements;

    public static Toast registerAClick(boolean beatLevel, int levelID, boolean win, Context context) {
        // TODO: Do something with the level info
        return registerAClick(win, context);
    }

    public static Toast registerAClick(boolean win, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("progress", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int clicks = sharedPreferences.getInt("clicks", 0);
        int wins = sharedPreferences.getInt("wins", 0);
        clicks++;
        if (win) wins++;
        editor.putInt("clicks", clicks);
        editor.putInt("wins", wins);
        editor.apply();
        List<Achievement> achievementsReached = checkAchievements(context, clicks, wins);
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
            achievements = gson.fromJson(data, Achievement[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: Allow possibility of displaying multiple achievements at once?
    private static Toast generateAchievementToast(List<Achievement> achievementsReached,
                                                  Context context) {
        Achievement achievement = achievementsReached.get(0);

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = new View(context);
        view = view.findViewById(R.id.achievement_toast_container);
        View layout = inflater.inflate(R.layout.achievement_toast, (ViewGroup) view);

        TextView text = layout.findViewById(R.id.ach_toast_text);
        text.setText(achievement.getShortDesc());

        ImageView imageView = layout.findViewById(R.id.ach_toast_image);
        imageView.setImageResource(Util.getStringResourceByName(achievement.getIcon(), context));

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.TOP, 0, 40);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);

        return toast;
    }

    private static List<Achievement> checkAchievements(Context context, int clicks, int wins) {
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
