package fi.tuni.parsta;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ProgressController {
    private static ArrayList<Achievement> achievements;

    public static Toast registerAClick(boolean beatLevel, int levelID, boolean win, Context context) {
        // TODO: Do something with the level info
        return registerAClick(win, context);
    }

    public static Toast registerAClick(boolean win, Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
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
            achievements = gson.fromJson(data, new TypeToken<ArrayList<Achievement>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        readAchievementStatus(context);
    }

    private static void readAchievementStatus(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        for (Achievement a : achievements) {
            a.setUnlocked(sharedPreferences.getBoolean(a.getIdKey(), false));
            a.setDateOfAchievement(sharedPreferences.getString(a.getADateKey(), "-"));
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
        List<Achievement> achievements = getAchievements(context);
        List<Achievement> achievementsReached = new ArrayList<>();
        for (Achievement a : achievements) {
            if (a.checkIfCompleted(clicks, wins)) {
                a.unlock();
                SharedPreferences.Editor editor = getSharedPreferences(context).edit();
                editor.putBoolean(a.getIdKey(), true);
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd.M.yyyy");
                editor.putString(a.getADateKey(), df.format(c));
                editor.apply();
                achievementsReached.add(a);
            }
        }
        return achievementsReached;
    }

    public static ArrayList<Achievement> getAchievements(Context context) {
        if (achievements == null) initAchievements(context);
        return achievements;
    }

    public static int getWins(Context context) {
        return getSharedPreferences(context).getInt("wins", 0);
    }

    public static int getClicks(Context context) {
        return getSharedPreferences(context).getInt("clicks", 0);
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("progress", MODE_PRIVATE);
    }
}
