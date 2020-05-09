package fi.tuni.facingo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

import androidx.fragment.app.DialogFragment;

import static android.content.Context.MODE_PRIVATE;


public class ResetDialog extends DialogFragment {
    private TextView mActionOk, mActionCancel;
    SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_resetgame,container,false);
        mActionCancel = view.findViewById(R.id.action_cancel);
        mActionOk = view.findViewById(R.id.action_ok);
        sharedPreferences = getContext().getSharedPreferences("settings", MODE_PRIVATE);

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.vibrate(view, getContext());
                SettingsDialog dialog = new SettingsDialog();
                dialog.show(getFragmentManager(), "SettingsDialog");
                getDialog().dismiss();
            }
        });

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.vibrate(view, getContext());
                getContext().getSharedPreferences("settings",0).edit().clear().apply();
                getContext().getSharedPreferences("progress",0).edit().clear().apply();
                Log.d("SettingsDialog","Settings reset: ");
                getDialog().dismiss();

                //reset the language to phones default language if finnish, otherwise set to english
                if(Locale.getDefault().toString().equals("fi_FI") || Locale.getDefault().toString().equals("fi") ) {
                    updateSettingsPrefs("languageCurrently", "fi");
                } else {
                    updateSettingsPrefs("languageCurrently", "en");
                }
                Intent mainIntent = new Intent(getContext(), MainActivity.class );
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainIntent);
            }
        });

        return view;
    }
    private void updateSettingsPrefs(String field, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(field, value);
        editor.apply();
    }

    private String getLanguagePrefs(String field) {
        return sharedPreferences.getString(field, "");
    }

}
