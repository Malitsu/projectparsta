package fi.tuni.parsta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import static android.content.Context.MODE_PRIVATE;

public class AboutDialog extends DialogFragment {

    SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_about,container,false);
        sharedPreferences = getContext().getSharedPreferences("settings", MODE_PRIVATE);

        LinearLayout layout = view.findViewById(R.id.about_rel);
        ImageView tamkLogo = view.findViewById(R.id.tamklogo);
        ImageView tikoLogo = view.findViewById(R.id.tikologo);
        ImageView tampereLogo = view.findViewById(R.id.tamperelogo);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        Log.d("AboutDialog", getLanguagePrefs("languageCurrently") + " languageee");

        if(getLanguagePrefs("languageCurrently").equals("fi")) {
            tamkLogo.setImageResource(R.drawable.tamklogo);
            tikoLogo.setImageResource(R.drawable.tikologofin);
        } else {
            tamkLogo.setImageResource(R.drawable.tamklogoen);
            tikoLogo.setImageResource(R.drawable.tikologoen);
        }

        return view;
    }

    public void aboutBox(View v) {
        getDialog().dismiss();
    }

    private String getLanguagePrefs(String field) {
        return sharedPreferences.getString(field, "");
    }

}
