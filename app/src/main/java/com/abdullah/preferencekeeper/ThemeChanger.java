package com.abdullah.preferencekeeper;

import androidx.appcompat.app.AppCompatDelegate;

public class ThemeChanger {
    public void changeTheme(String mode)
    {
        if(mode.equals("Light"))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        else if(mode.equals("Dark"))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }
}
