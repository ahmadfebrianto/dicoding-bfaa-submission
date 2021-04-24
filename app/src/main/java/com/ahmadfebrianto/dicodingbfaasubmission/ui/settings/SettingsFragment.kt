package com.ahmadfebrianto.dicodingbfaasubmission.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.ahmadfebrianto.dicodingbfaasubmission.R
import java.text.SimpleDateFormat
import java.util.*

class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var mAlarm: String
    private lateinit var mNotification: String
    private lateinit var alarmPreference: SwitchPreference
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(R.xml.preferences)

        init()
        setSummaries()
        alarmReceiver = AlarmReceiver()
    }

    private fun init() {
        mAlarm = resources.getString(R.string.key_reminder)
        mNotification = resources.getString(R.string.key_reminder)
        alarmPreference = findPreference<SwitchPreference>(mAlarm) as SwitchPreference
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        when (key) {
            mAlarm -> {
                val sh = preferenceManager.sharedPreferences
                if (sh.getBoolean(mAlarm, false)) {
                    setAlarmTime()
                } else {
                    alarmReceiver.cancelAlarm(
                        requireContext(),
                        AlarmReceiver.TYPE_REPEATING
                    )
                }
                setSummaries()
            }
        }
    }

    private fun setAlarmTime() {
        val timePickerFragmentRepeat = TimePickerFragment()
        val mFragmentManager = childFragmentManager
        timePickerFragmentRepeat.show(
            mFragmentManager,
            TimePickerFragment::class.java.simpleName
        )

    }

    private fun setRepeatAlarm(repeatTime: String) {
        alarmReceiver.setRepeatingAlarm(
            requireContext(),
            repeatTime
        )

    }

    private fun setSummaries() {
        alarmPreference.summaryOn = getString(R.string.reminder_on)
    }

    internal var dialogTimeListener: TimePickerFragment.DialogTimeListener =
        object : TimePickerFragment.DialogTimeListener {
            override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
                val calendar = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, hourOfDay)
                    set(Calendar.MINUTE, minute)
                }

                val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                val timeTaken = dateFormat.format(calendar.time)
                alarmPreference.summaryOn = getString(R.string.reminder_on) + " " + timeTaken
                setRepeatAlarm(timeTaken)
            }

            override fun onCancel() {
                alarmPreference.isChecked = false

            }
        }


}