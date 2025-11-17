package com.abdullah.preferencekeeper;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProfileView extends Fragment {

    ThemeChanger changer;
    TextView name, email, password, phone, gender, notif, theme;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ProfileView() {
        // Required empty public constructor
    }
    public static ProfileView newInstance(String param1, String param2) {
        ProfileView fragment = new ProfileView();
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

        return inflater.inflate(R.layout.fragment_profile_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        phone = view.findViewById(R.id.phone);
        gender = view.findViewById(R.id.gender);
        notif = view.findViewById(R.id.notifs);
        theme = view.findViewById(R.id.theme);
        changer = new ThemeChanger();
        displayPrefs();
    }

    private void displayPrefs()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        String n = prefs.getString("username", "");
        String e = prefs.getString("email", "Not Set");
        String p = prefs.getString("password", "Not Set");
        String ph = prefs.getString("phone", "Not Set");
        String g = prefs.getString("gender", "Not Set");
        String ntfs = prefs.getString("notifs", "Disabled");
        String th = prefs.getString("theme", "Light");
        changer.changeTheme(th);
        name.setText(n);
        email.setText(e);
        password.setText(p);
        phone.setText(ph);
        gender.setText(g);
        notif.setText(ntfs);
        theme.setText(th);
    }

}