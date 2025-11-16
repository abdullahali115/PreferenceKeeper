package com.abdullah.preferencekeeper;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDelegate;

public class ProfileView extends Fragment {

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

        View view = inflater.inflate(R.layout.fragment_profile_view, container, false);

        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        phone = view.findViewById(R.id.phone);
        gender = view.findViewById(R.id.gender);
        notif = view.findViewById(R.id.notifs);
        theme = view.findViewById(R.id.theme);
        displayPrefs();

        return view;
    }

    private void displayPrefs()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        String n = prefs.getString("username", "John Doe");
        String e = prefs.getString("email", "john@doe.com");
        String p = prefs.getString("password", "doe_john");
        String ph = prefs.getString("phone", "+923000000000");
        String g = prefs.getString("gender", "Male");
        String ntfs = prefs.getString("notifs", "Disabled");
        String th = prefs.getString("theme", "Light");
        name.setText(n);
        email.setText(e);
        password.setText(p);
        phone.setText(ph);
        gender.setText(g);
        notif.setText(ntfs);
        theme.setText(th);
    }

}