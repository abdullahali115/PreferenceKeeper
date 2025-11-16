package com.abdullah.preferencekeeper;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    TabLayout navBar;
    Fragment frag;
    FragmentManager manager;
    FragmentTransaction transactor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        frag = new ProfileView();
        manager = getSupportFragmentManager();
        transactor = manager.beginTransaction();
        transactor.replace(R.id.frame, frag);
        transactor.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transactor.commit();
        navBar = findViewById(R.id.menu);

        navBar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment selectedFrag = null;
                switch(tab.getPosition())
                {
                    case 0:
                        selectedFrag = new ProfileView();
                        break;
                    case 1:
                        selectedFrag = new UserSettings();
                        break;
                }
                if(selectedFrag != null)
                {
                    manager = getSupportFragmentManager();
                    transactor = manager.beginTransaction();
                    transactor.replace(R.id.frame, selectedFrag);
                    transactor.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transactor.commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}