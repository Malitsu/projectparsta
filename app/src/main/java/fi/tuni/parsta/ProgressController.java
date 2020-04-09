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
    private final static String PROGRESSPREF = "progress";
    private final static String LEVEL = "level_";
    private final static String MAXSCORE = "_maxscore";
    private final static String CURRENTCLICKS = "_currentclicks";
    private final static String CURRENTSCORE = "_currentscore";
    private final static String RIGHTANSWERAMOUNT = "_rightansweramount";
    private final static String RIGHT = "_right";
    private final static String PICKNRO = "_picknro_";
    private final static String CURRENTLEVELINPROGRESS = "currentlevelinprogress";
    private final static String MAXPROGRESSLEVEL = "maxprogresslevel";

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

    public static void updateLevelProgress(Context context, int level, int newScore, int newCurrentLevelClicks, boolean wasAnswerCorrect) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(LEVEL + level + PICKNRO + newCurrentLevelClicks + RIGHT, wasAnswerCorrect);
        editor.putInt(LEVEL + level + CURRENTSCORE, newScore);
        editor.putInt(LEVEL + level + CURRENTCLICKS, newCurrentLevelClicks);
        editor.apply();
        setLevelMaxScore(context, level, newScore);
    }

    public static int getLevelMaxScore(Context context, int level) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        int levelMaxScore =  sharedPreferences.getInt(LEVEL + level + MAXSCORE,0);
        return levelMaxScore;
    }

    public static void setLevelMaxScore(Context context, int level, int currentMaxScore) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int prevMax = getLevelMaxScore(context,level);
        int newMax = currentMaxScore;
        if(prevMax < newMax) {
            editor.putInt(LEVEL + level + MAXSCORE,currentMaxScore);
            editor.apply();
        }
    }

    public static int getLevelCurrentScore(Context context, int level) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        int levelCurrentScore =  sharedPreferences.getInt(LEVEL + level + CURRENTSCORE,0);
        return levelCurrentScore;
    }

    public static void setLevelCurrentScore(Context context, int level, int currentScore) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LEVEL + level + CURRENTSCORE, currentScore);
        editor.apply();
    }

    public static void setLevelCurrentClicks(Context context, int level, int currentClicks) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LEVEL + level + CURRENTCLICKS, currentClicks);
        editor.apply();
    }

    public static int getLevelCurrentClicks(Context context, int level) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        int levelCurrentClicks =  sharedPreferences.getInt(LEVEL + level + CURRENTCLICKS,0);
        return levelCurrentClicks;
    }

    public static boolean[] getLevelRightAnswerProgress(Context context, int level){
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        boolean[] rightAnswerArray = new boolean[10];

        for(int i = 0; i < 10; i++) {
            rightAnswerArray[i] = sharedPreferences.getBoolean(LEVEL + level + PICKNRO + (i + 1) + RIGHT, false);
        }
        return rightAnswerArray;
    }

    public static void setLevelRightAnswerProgress(Context context, int level, int clicks, boolean wasRight){
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(LEVEL + level + PICKNRO + (clicks+1) + RIGHT, wasRight);
        editor.apply();
    }

    public static boolean[] resetLevelRightAnswerProgress(Context context, int level) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean[] rightAnswerArray = new boolean[10];

        for(int i = 0; i < 10; i++) {
            editor.putBoolean(LEVEL + level + PICKNRO + (i+1) + RIGHT, false);
            editor.apply();
        }
        return rightAnswerArray;
    }

    public static int getCurrentLevel(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        int curLevel = sharedPreferences.getInt(LEVEL + CURRENTLEVELINPROGRESS,1);
        return curLevel;
    }

    public static void setCurrentLevelInProgress(Context context, int level) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LEVEL + CURRENTLEVELINPROGRESS, level);
        editor.apply();
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
        return context.getSharedPreferences(PROGRESSPREF, MODE_PRIVATE);
    }
}
