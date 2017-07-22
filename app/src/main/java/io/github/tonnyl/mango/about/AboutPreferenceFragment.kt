package io.github.tonnyl.mango.about

import android.os.Bundle
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import io.github.tonnyl.mango.R
import org.jetbrains.anko.browse
import org.jetbrains.anko.email

/**
 * Created by lizhaotailang on 2017/7/21.
 */

class AboutPreferenceFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.about_screen)

        findPreference("contributors").onPreferenceClickListener = Preference.OnPreferenceClickListener {
            context.browse(getString(R.string.contributors_desc), true)
            true
        }

        findPreference("source_code").onPreferenceClickListener = Preference.OnPreferenceClickListener {
            context.browse(getString(R.string.source_code_desc), true)
            true
        }

        findPreference("feedback").onPreferenceClickListener = Preference.OnPreferenceClickListener {
            context.email(getString(R.string.feedback_email), getString(R.string.feedback_email_subject))
            true
        }
    }
}