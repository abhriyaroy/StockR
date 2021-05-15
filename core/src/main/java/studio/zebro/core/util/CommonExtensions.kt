package studio.zebro.core.util

import android.content.Context
import android.os.Handler
import android.os.Looper

fun withDelayOnMain(delay: Long, block: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(Runnable(block), delay)
}

fun Context?.convertDpToPx(dps: Int): Int {
    var pixels = 0
    if (this != null) {
        val scale = resources.displayMetrics.density
        pixels = (dps * scale + 0.5f).toInt()
    }
    return pixels
}