package com.lena.androidrest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }

    public static class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.my_preferences);
            restorePreferencesValues();
        }

        private void restorePreferencesValues() {
            EditTextPreference preferenceLogin = (EditTextPreference) findPreference("pref_login");
            String value = preferenceLogin.getText();
            if (value != null) {
                preferenceLogin.setSummary(value);
            }

            EditTextPreference preferenceFio = (EditTextPreference) findPreference("pref_fio");
            value = preferenceFio.getText();
            if (value != null) {
                preferenceFio.setSummary(value);
            }

            EditTextPreference preferencePost = (EditTextPreference) findPreference("pref_post");
            value = preferencePost.getText();
            if (value != null) {
                preferencePost.setSummary(value);
            }
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = super.onCreateView(inflater, container, savedInstanceState);

            if (view != null) {
                //view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
            return view;
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            switch(key) {
                case "pref_login": {
                    EditTextPreference preference = (EditTextPreference) findPreference(key);
                    preference.setSummary(sharedPreferences.getString(key, ""));
                } break;

                case "pref_fio": {
                    EditTextPreference preference = (EditTextPreference) findPreference(key);
                    preference.setSummary(sharedPreferences.getString(key, ""));
                } break;

                case "pref_post": {
                    EditTextPreference preference = (EditTextPreference) findPreference(key);
                    preference.setSummary(sharedPreferences.getString(key, ""));
                } break;

                case "pref_password": {
                    //keep hidden summary
                } break;
            }
        }

        @Override
        public void onResume() {
            super.onResume();

            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();

            getPreferenceScreen().getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(this);
        }
    }
}
