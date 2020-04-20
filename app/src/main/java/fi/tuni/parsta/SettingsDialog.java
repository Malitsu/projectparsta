package fi.tuni.parsta;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import static android.content.Context.MODE_PRIVATE;

public class SettingsDialog extends DialogFragment {
    private CheckBox soundSettings;
    private TextView mActionOk, mActionCancel;
    private boolean soundEffectsOn;
    SharedPreferences sharedPreferences;
    RadioButton finnish;
    RadioButton english;
    private boolean finnishPicked;
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
        finnishPicked = getSettingsPrefs("language");
        vibrationOn = getSettingsPrefs("vibration");
        //set the checkbox to the past settings (true or false).
        soundSettings.setChecked(soundEffectsOn);
        vibrationSettings.setChecked(vibrationOn);
        Log.d("SettingsDialog", getSettingsPrefs("language") + " languageee");

        if(finnishPicked) {
            finnish.setChecked(true);
        } else {
            english.setChecked(true);
        }


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.finnish) {
                    finnishPicked = true;
                } else if (checkedId == R.id.english) {
                    finnishPicked = false;
                }

            }

        });
        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save this to memory when ever that option been created
                updateSettingsPrefs("sound", soundSettings.isChecked());
                updateSettingsPrefs("language", finnishPicked);
                updateSettingsPrefs("vibration", vibrationSettings.isChecked());
                Log.d("SettingsDialog","Clicked on sound effect settings and it is currently: " + soundEffectsOn);
                getDialog().dismiss();
            }
        });

        resetApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

}
