package org.techtown.unswebview

import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.appcompat.app.ActionBar
import androidx.core.view.WindowCompat

object Utils {
    fun applyFullScreen(supportActionBar: ActionBar?, window: Window) {
        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            WindowCompat.setDecorFitsSystemWindows(window, false)

            val controller = window.insetsController
            controller?.apply {
                this.hide(WindowInsets.Type.systemBars() or WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                this.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }
}