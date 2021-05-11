package studio.zebro.core.util

import android.os.Handler
import android.os.Looper

fun withDelayOnMain(delay: Long, block: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(Runnable(block), delay)
}