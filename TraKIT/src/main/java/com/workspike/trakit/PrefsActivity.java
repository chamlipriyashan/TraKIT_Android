package com.workspike.trakit;

import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Chamli Priyashan on 7/11/2016.
 */
public class PrefsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
        getListView().setCacheColorHint(Color.TRANSPARENT);
        getListView().setBackgroundColor(Color.rgb(4, 26, 55));
    }

}