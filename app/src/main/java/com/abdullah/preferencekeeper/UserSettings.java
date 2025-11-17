package com.abdullah.preferencekeeper;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class UserSettings extends Fragment {

    ThemeChanger changer;
    EditText nameSetter, emailSetter, passwordSetter, phoneSetter;
    Button saveBtn, resetBtn;
    RadioGroup genderSetter, notifSetter, themeSetter;
    RadioButton genderBtn, notifBtn, themeBtn;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public UserSettings() {
    }

    public static UserSettings newInstance(String param1, String param2) {
        UserSettings fragment = new UserSettings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameSetter = view.findViewById(R.id.setName);
        emailSetter = view.findViewById(R.id.setEmail);
        passwordSetter = view.findViewById(R.id.setPassword);
        phoneSetter = view.findViewById(R.id.setPhone);
        genderSetter = view.findViewById(R.id.setGender);
        notifSetter = view.findViewById(R.id.setNotifs);
        themeSetter = view.findViewById(R.id.themeSetter);
        saveBtn = view.findViewById(R.id.saveBtn);
        resetBtn = view.findViewById(R.id.resetBtn);
        changer = new ThemeChanger();
        loadPreviousPrefs();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameSetter.getText().toString();
                String email = emailSetter.getText().toString();
                String password = passwordSetter.getText().toString();
                String phone = phoneSetter.getText().toString();
                int genderID = genderSetter.getCheckedRadioButtonId();
                int notifsID = notifSetter.getCheckedRadioButtonId();
                int themeID = themeSetter.getCheckedRadioButtonId();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
                SharedPreferences.Editor editor = prefs.edit();

                String val = validator(name, email, password, phone);
                if(!val.isEmpty())
                {
                    Toast.makeText(requireContext(), val, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(!name.isEmpty())
                        editor.putString("username", name);
                    if(!email.isEmpty())
                        editor.putString("email", email);
                    if(!password.isEmpty())
                        editor.putString("password", password);
                    if(!phone.isEmpty())
                        editor.putString("phone", phone);
                    if(genderID != -1)
                    {
                        genderBtn = view.findViewById(genderID);
                        editor.putString("gender", genderBtn.getText().toString());
                    }
                    if(notifsID != -1)
                    {
                        notifBtn = view.findViewById(notifsID);
                        String temp = notifBtn.getText().toString();
                        if(temp.equals("On"))
                            temp = "Enabled";
                        else if(temp.equals("Off"))
                            temp = "Disabled";
                        editor.putString("notifs", temp);
                    }
                    if(themeID != -1)
                    {
                        themeBtn = view.findViewById(themeID);
                        String th = themeBtn.getText().toString();
                        editor.putString("theme", th);
                        changer.changeTheme(th);
                    }
                    editor.apply();
                    Toast.makeText(requireContext(), "Data Saved!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(requireContext(), "Hold to Reset!", Toast.LENGTH_SHORT).show();
            }
        });
        resetBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.apply();
                changer.changeTheme("Light");
                loadPreviousPrefs();
                resetGender(genderSetter);
                Toast.makeText(requireContext(), "Preferences Reset!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    public String validator(String name, String email, String password, String phone)
    {
        if(!name.isEmpty() && !name.matches("[a-zA-z ]+"))
        {
            return "Invalid Name!";
        }
        if(!email.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            return "Invalid Email!";
        }
        if(!password.isEmpty() && password.length() < 8)
        {
            return "Password can't be less than 8 characters";
        }
        if(!phone.isEmpty() && !phone.matches("\\+?\\d{10,15}"))
        {
            return "Invalid Phone Number!";
        }
        return "";
    }
    private void loadPreviousPrefs()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        String gen = prefs.getString("gender", "");
        String notif = prefs.getString("notifs", "Disabled");
        String theme = prefs.getString("theme", "Light");
        if(!gen.isEmpty())
            radioHelper(genderSetter, gen);
        radioHelper(notifSetter, notif);
        radioHelper(themeSetter, theme);
    }
    private void radioHelper(RadioGroup rg, String val)
    {
        if(!val.isEmpty())
        {
            for (int i = 0; i < rg.getChildCount(); i++) {
                RadioButton rb = (RadioButton) rg.getChildAt(i);
                if(rb.getText().toString().equals(val))
                    rg.check(rb.getId());
                else if(rb.getText().toString().equals("On") && val.equals("Enabled"))
                    rg.check(rb.getId());
                else if(rb.getText().toString().equals("Off") && val.equals("Disabled"))
                    rg.check(rb.getId());
            }
        }
    }
    private void resetGender(RadioGroup gen)
    {
        for(int i=0;i<gen.getChildCount();i++)
        {
            RadioButton btn = (RadioButton) gen.getChildAt(i);
            btn.setChecked(false);
        }
    }


}