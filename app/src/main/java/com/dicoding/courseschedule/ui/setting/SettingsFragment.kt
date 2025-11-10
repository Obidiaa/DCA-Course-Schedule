package com.dicoding.courseschedule.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.NightMode

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        // 10 : Update theme based on value in ListPreference
        val themePreference = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        themePreference?.setOnPreferenceChangeListener { _, newValue ->
            val nightMode = when (newValue) {
                "auto" -> NightMode.AUTO.value
                "on" -> NightMode.ON.value
                else -> NightMode.OFF.value
            }
            updateTheme(nightMode)
        }

        // 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        val reminderPreference = findPreference<SwitchPreferenceCompat>(getString(R.string.pref_key_notify))
        reminderPreference?.setOnPreferenceChangeListener { _, newValue ->
            val isActive = newValue as Boolean
            val dailyReminder = DailyReminder()
            if (isActive) {
                dailyReminder.setDailyReminder(requireContext())
            } else {
                dailyReminder.cancelAlarm(requireContext())
            }
            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}