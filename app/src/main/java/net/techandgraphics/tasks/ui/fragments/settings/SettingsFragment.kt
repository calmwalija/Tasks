package net.techandgraphics.tasks.ui.fragments.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import net.techandgraphics.tasks.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}