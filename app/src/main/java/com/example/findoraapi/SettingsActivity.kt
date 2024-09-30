package com.example.findoraapi

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.Preference
import androidx.preference.SwitchPreferenceCompat
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceManager

class SettingsActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set up fragment
        supportFragmentManager
            .beginTransaction()
            .replace(android.R.id.content, SettingsFragment())
            .commit()
    }
    // Fragment class to load the preference screen
    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)

            // Log out functionality
            val logoutPref: Preference? = findPreference("logout")
            logoutPref?.setOnPreferenceClickListener {

                // Here you can handle logging out logic (e.g., clear session, etc.)
                Toast.makeText(activity, "Logging out...", Toast.LENGTH_SHORT).show()
                // Redirect to LoginActivity or perform logout
                startActivity(Intent(activity, Login::class.java))
                true
            }
            // Profile Privacy Toggle
            val profilePrivacyPreference = findPreference<ListPreference>("profile_privacy")
            profilePrivacyPreference?.summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()

            profilePrivacyPreference?.setOnPreferenceChangeListener { preference, newValue ->
                val newPrivacySetting = newValue as String
                val summaryText = if (newPrivacySetting == "public") {
                    "Your profile is public and can be viewed by others."
                } else {
                    "Your profile is private and only visible to you."
                }
                preference.summary = summaryText
                true
            }
//(No date) YouTube. YouTube. Available at: https://www.youtube.com/watch?v=e09sAKgMPBA (Accessed: 22 September 2024).
            // Rewards
            val rewardsPreference = findPreference<Preference>("rewards")
            rewardsPreference?.setOnPreferenceClickListener {
                AlertDialog.Builder(context)
                    .setTitle("Rewards")
                    .setMessage("Earn rewards by attending or hosting local events. " +
                            "For every 5 events attended, you'll receive a discount on your next ticket. " +
                            "Hosts earn rewards based on the success and feedback of their events.")
                    .setPositiveButton("OK", null)
                    .show()
                true
            }
            val helpPreference = findPreference<Preference>("help")
            helpPreference?.setOnPreferenceClickListener {
                AlertDialog.Builder(context)
                    .setTitle("Help")
                    .setMessage("Welcome to Findora Help.\n\n" +
                            "Here’s how you can use the app:\n\n" +
                            "1. **Find Events**: Use the search function on the main screen to discover events happening near you. You can filter events by category, location, and date.\n\n" +
                            "2. **Create Events**: If you’re an event host, use the 'Create Event' option to post your event. Fill in all necessary details such as event name, location, date, and ticket information.\n\n" +
                            "3. **Manage Profile**: You can update your profile details from the settings, choose privacy options, and even enable/disable notifications and location tracking.\n\n" +
                            "4. **Support**: For any issues, feel free to reach out to our support team at support@findora.com. We are here to help!")
                    .setPositiveButton("OK", null)
                    .show()
                true
            }

            // About Us
            val aboutUsPreference = findPreference<Preference>("about_us")
            aboutUsPreference?.setOnPreferenceClickListener {
                AlertDialog.Builder(context)
                    .setTitle("About Findora")
                    .setMessage("Findora is your go-to app for finding and creating local events. " +
                            "Our mission is to connect people with the best local happenings in their area. " +
                            "Whether you're an event-goer or a host, Findora makes it easy to create, find, and explore events near you.")
                    .setPositiveButton("OK", null)
                    .show()
                true
            }

            // Privacy Policy
            val privacyPolicyPreference = findPreference<Preference>("privacy_policy")
            privacyPolicyPreference?.setOnPreferenceClickListener {
                AlertDialog.Builder(context)
                    .setTitle("Privacy Policy")
                    .setMessage("Findora takes your privacy seriously. We collect minimal information such as your location (with permission) " +
                            "to help you find events near you. Any personal information you provide (such as name, email) is securely stored " +
                            "and not shared with third parties without your consent.")
                    .setPositiveButton("OK", null)
                    .show()
                true
            }

            // Terms & Conditions
            val termsConditionsPreference = findPreference<Preference>("terms_conditions")
            termsConditionsPreference?.setOnPreferenceClickListener {
                AlertDialog.Builder(context)
                    .setTitle("Terms and Conditions")
                    .setMessage("By using Findora, you agree to our terms and conditions. As a user, you must ensure that all event postings are accurate, " +
                            "respectful, and lawful. We reserve the right to remove any inappropriate content. Findora is not liable for any events " +
                            "hosted through the platform. Users are responsible for their own safety and behavior at events.")
                    .setPositiveButton("OK", null)
                    .show()
                true
            }
            //(No date) YouTube. YouTube. Available at: https://www.youtube.com/watch?v=e09sAKgMPBA (Accessed: 20 September 2024).
            // Theme change handling (Light/Dark mode)
            val themePref: Preference? = findPreference("theme")
            themePref?.setOnPreferenceChangeListener { _, newValue ->
                when (newValue) {
                    "light" -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                    "dark" -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                }
                true
            }

            // Push Notifications toggle
            val pushNotificationPref: SwitchPreferenceCompat? = findPreference("push_notifications")
            pushNotificationPref?.setOnPreferenceChangeListener { _, newValue ->
                val enabled = newValue as Boolean
                if (enabled) {
                    Toast.makeText(activity, "Push Notifications Enabled", Toast.LENGTH_SHORT).show()
                    // Handle enabling push notifications here
                } else {
                    Toast.makeText(activity, "Push Notifications Disabled", Toast.LENGTH_SHORT).show()
                    // Handle disabling push notifications here
                }
                true
            }

            // Location Services toggle
            val locationPref: SwitchPreferenceCompat? = findPreference("location_services")
            locationPref?.setOnPreferenceChangeListener { _, newValue ->
                val enabled = newValue as Boolean
                if (enabled) {
                    Toast.makeText(activity, "Location Services Enabled", Toast.LENGTH_SHORT).show()
                    // Handle enabling location services here
                } else {
                    Toast.makeText(activity, "Location Services Disabled", Toast.LENGTH_SHORT).show()
                    // Handle disabling location services here
                }
                true
            }
        }
    }
}