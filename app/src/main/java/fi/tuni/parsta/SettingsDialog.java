package fi.tuni.parsta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Locale;

import androidx.fragment.app.DialogFragment;

import static android.content.Context.MODE_PRIVATE;

public class SettingsDialog extends DialogFragment {
    private CheckBox soundSettings;
    private TextView mActionOk, mActionCancel;
    private boolean soundEffectsOn;
    SharedPreferences sharedPreferences;
    RadioButton finnish;
    RadioButton english;
    RadioGroup radioGroup;
    private CheckBox vibrationSettings;
    private boolean vibrationOn;
    private Button resetApp;
    ResetDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_settings,container,false);
        sharedPreferences = getContext().getSharedPreferences("settings", MODE_PRIVATE);

        mActionCancel = view.findViewById(R.id.action_cancel);
        mActionOk = view.findViewById(R.id.action_ok);
        soundSettings = view.findViewById(R.id.sound_effect_on);
        vibrationSettings = view.findViewById(R.id.vibration);
        finnish = view.findViewById(R.id.finnish);
        english = view.findViewById(R.id.english);
        radioGroup = view.findViewById(R.id.radio);
        resetApp = view.findViewById(R.id.reset_app);

        //get this from saved memory when that is created.
        soundEffectsOn = getSettingsPrefs("sound");
        vibrationOn = getSettingsPrefs("vibration");
        //set the checkbox to the past settings (true or false).
        soundSettings.setChecked(soundEffectsOn);
        vibrationSettings.setChecked(vibrationOn);
        Log.d("SettingsDialog", getSettingsPrefs("language") + " languageee");

        if(getLanguagePrefs("languageCurrently").equals("fi")) {
            finnish.setChecked(true);
        } else {
            english.setChecked(true);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.finnish) {
                    Util.setAppLocale("fi", getContext());
                } else if (checkedId == R.id.english) {
                    Util.setAppLocale("en", getContext());
                }

            }

        });
        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.vibrate(view, getContext()); getDialog().dismiss();
            }
        });

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.vibrate(view, getContext());

                //save this to memory when ever that option been created
                updateSettingsPrefs("sound", soundSettings.isChecked());
                updateSettingsPrefs("vibration", vibrationSettings.isChecked());

                Log.d("SettingsDialog","Clicked on sound effect settings and it is currently: " + soundEffectsOn);
                getDialog().dismiss();
                Intent mainIntent = new Intent(getContext(), MainActivity.class );
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainIntent);

            }
        });

        resetApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.vibrate(view, getContext());
                dialog = new ResetDialog();
                dialog.show(getFragmentManager(), "ResetDialog");
                getDialog().dismiss();
            }
        });
        return view;
    }

    private void updateSettingsPrefs(String field, Boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(field, value);
        editor.apply();
    }


    private Boolean getSettingsPrefs(String field) {
        return sharedPreferences.getBoolean(field, true);
    }

    private String getLanguagePrefs(String field) {
        return sharedPreferences.getString(field, "");
    }

}
