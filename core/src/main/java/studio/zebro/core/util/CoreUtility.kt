package studio.zebro.core.util

import androidx.navigation.NavOptions
import studio.zebro.core.R

object CoreUtility {

    fun getDefaultNavigationAnimation() = NavOptions.Builder().apply {
        this.setEnterAnim(R.anim.slide_in_right).setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left).setPopExitAnim(R.anim.slide_out_right)
    }.build()

}