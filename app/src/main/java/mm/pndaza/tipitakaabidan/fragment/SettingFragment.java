package mm.pndaza.tipitakaabidan.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import mm.pndaza.tipitakaabidan.R;
import mm.pndaza.tipitakaabidan.utils.MDetect;
import mm.pndaza.tipitakaabidan.utils.Rabbit;
import mm.pndaza.tipitakaabidan.utils.SharePref;

public class SettingFragment extends Fragment {

    private OnSettingChangeListener onSettingChangeListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(MDetect.getDeviceEncodedText(getString(R.string.title_setting_mm)));
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MDetect.init(this.getContext());
        HtmlTextView htmlTextView = view.findViewById(R.id.tv_info);
        htmlTextView.setHtml(MDetect.getDeviceEncodedText(getInfo()));

        RadioGroup radioGroupTheme = view.findViewById(R.id.rg_theme);
        initView(radioGroupTheme);
        initViewTextEncoding(view);

        radioGroupTheme.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            saveAndRestart(checkedId);
        });

    }

    private void initView( RadioGroup radioGroupTheme){
        SharePref sharePref = SharePref.getInstance(this.getContext());
        int nightModeState = sharePref.getPrefNightModeState();

        if(nightModeState == 0){
            radioGroupTheme.check(R.id.radio_theme_day);
        } else if (nightModeState == 1){
            radioGroupTheme.check((R.id.radio_theme_night));
        } else {
            radioGroupTheme.check((R.id.radio_theme_night_full));
        }

    }

    private void saveAndRestart(int checkedId){
        SharePref sharePref = SharePref.getInstance(this.getContext());
        int nightModeState = 0;
        switch (checkedId){
            case R.id.radio_theme_day:
                nightModeState = 0;
                break;
            case R.id.radio_theme_night:
                nightModeState = 1;
                break;
            case R.id.radio_theme_night_full:
                nightModeState = 2;;

                break;
        }
        sharePref.setPrefNightModeState(nightModeState);
        onSettingChangeListener.onChangeListener();
    }

    private void initViewTextEncoding(View view){
        RadioButton rbThemeDay = view.findViewById(R.id.radio_theme_day);
        RadioButton rbThemeNight = view.findViewById(R.id.radio_theme_night);

        String themeDay = getString(R.string.themeDay);
        String themeNight = getString(R.string.themeNight);

        if(!MDetect.isUnicode()){
            rbThemeDay.setText(Rabbit.uni2zg(themeDay));
            rbThemeNight.setText(Rabbit.uni2zg(themeNight));
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            onSettingChangeListener = (OnSettingChangeListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implemented OnSettingChangeListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnSettingChangeListener{
        void onChangeListener();
    }

    private String getInfo() {
        StringBuilder info = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getContext().getAssets().open("info.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                info.append(line);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return info.toString();
    }

}
