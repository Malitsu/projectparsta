package fi.tuni.parsta;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
}
