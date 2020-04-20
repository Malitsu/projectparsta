package fi.tuni.parsta;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;


public class ResetDialog extends DialogFragment {
    private TextView mActionOk, mActionCancel;
    SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_resetgame,container,false);
        mActionCancel = view.findViewById(R.id.action_cancel);
        mActionOk = view.findViewById(R.id.action_ok);

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsDialog dialog = new SettingsDialog();
                dialog.show(getFragmentManager(), "SettingsDialog");
                getDialog().dismiss();
            }
        });

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().getSharedPreferences("settings",0).edit().clear().apply();
                getContext().getSharedPreferences("progress",0).edit().clear().apply();
                Log.d("SettingsDialog","Settings reset: ");
                getDialog().dismiss();
            }
        });

        return view;
    }


}
