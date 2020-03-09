package fi.tuni.parsta;

import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

public class SettingsDialog extends DialogFragment {
    private CheckBox soundSettings;
    private TextView mActionOk, mActionCancel;
    private boolean soundEffectsOn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_settings,container,false);
        mActionCancel = view.findViewById(R.id.action_cancel);
        mActionOk = view.findViewById(R.id.action_ok);
        soundSettings = view.findViewById(R.id.sound_effect_on);
        //get this from saved memory when that is created.
        soundEffectsOn = true;
        //set the checkbox to the past settings (true or false).
        soundSettings.setChecked(soundEffectsOn);

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
                soundEffectsOn = soundSettings.isChecked();
                Log.d("SettingsDialog","Clicked on sound effect settings and it is currently: " + soundEffectsOn);
                getDialog().dismiss();
            }
        });
        return view;
    }

}
