package io.github.muntashirakon.music.extensions

import android.content.Context
import android.content.res.Configuration
import android.os.PowerManager
import androidx.annotation.IntegerRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import io.github.muntashirakon.music.util.PreferenceUtil

fun Fragment.getIntRes(@IntegerRes int: Int): Int {
    return resources.getInteger(int)
}

fun Context.getIntRes(@IntegerRes int: Int): Int {
    return resources.getInteger(int)
}

val Context.generalThemeValue
    get() = PreferenceUtil.getGeneralThemeValue(isSystemDarkModeEnabled())


fun Context.isSystemDarkModeEnabled(): Boolean {
    val isBatterySaverEnabled =
        (getSystemService(Context.POWER_SERVICE) as PowerManager?)?.isPowerSaveMode ?: false
    val isDarkModeEnabled =
        (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    return isBatterySaverEnabled or isDarkModeEnabled
}


inline fun <reified T : Any> Fragment.extra(key: String, default: T? = null) = lazy {
    val value = arguments?.get(key)
    if (value is T) value else default
}

inline fun <reified T : Any> Fragment.extraNotNull(key: String, default: T? = null) = lazy {
    val value = arguments?.get(key)
    requireNotNull(if (value is T) value else default) { key }
}

val NavHostFragment.currentFragment: Fragment?
    get() = targetFragment

val FragmentManager.currentNavigationFragment: Fragment?
    get() = primaryNavigationFragment?.childFragmentManager?.fragments?.first()

fun AppCompatActivity.currentFragment(navHostId: Int): Fragment? {
    val navHostFragment: NavHostFragment =
        supportFragmentManager.findFragmentById(navHostId) as NavHostFragment
    navHostFragment.targetFragment
    return navHostFragment?.childFragmentManager?.fragments?.first()
}
