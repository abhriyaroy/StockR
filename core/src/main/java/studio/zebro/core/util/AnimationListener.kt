package studio.zebro.core.util

import android.animation.Animator
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import com.airbnb.lottie.LottieAnimationView

private class AnimationListener(
    private val onAnimationRepeat: () -> Unit,
    private val onAnimationStart: () -> Unit,
    private val onAnimationEnd: () -> Unit
) : Animation.AnimationListener {
    override fun onAnimationRepeat(animation: Animation) = onAnimationRepeat()
    override fun onAnimationStart(animation: Animation) = onAnimationStart()
    override fun onAnimationEnd(animation: Animation) = onAnimationEnd()
}


fun View.showAnimation(
    @AnimRes animResId: Int,
    fillAfter: Boolean = true,
    onAnimationRepeat: () -> Unit = {},
    onAnimationStart: () -> Unit = {},
    onAnimationEnd: () -> Unit = {}
) = with(AnimationUtils.loadAnimation(context, animResId)) {
    setAnimationListener(AnimationListener(onAnimationRepeat, onAnimationStart, onAnimationEnd))
    this.fillAfter = fillAfter
    startAnimation(this)
}

fun LottieAnimationView.addOnAnimationListener(
    onAnimationRepeat: (animation: Animator) -> Unit = {},
    onAnimationStart: (animation: Animator) -> Unit = {},
    onAnimationEnd: (animation: Animator) -> Unit = {},
    onAnimationCancel: (animation: Animator) -> Unit = {}
) = this.addAnimatorListener(object : Animator.AnimatorListener {
    override fun onAnimationStart(animation: Animator) {
        onAnimationStart(animation)
    }

    override fun onAnimationEnd(animation: Animator) {
        onAnimationEnd(animation)
    }

    override fun onAnimationCancel(animation: Animator) {
        onAnimationCancel(animation)
    }

    override fun onAnimationRepeat(animation: Animator) {
        onAnimationRepeat(animation)
    }
})

fun Animator.addOnAnimationListener(
    onAnimationRepeat: (animation: Animator) -> Unit = {},
    onAnimationStart: (animation: Animator) -> Unit = {},
    onAnimationEnd: (animation: Animator) -> Unit = {},
    onAnimationCancel: (animation: Animator) -> Unit = {}
) = this.addListener(object : Animator.AnimatorListener {
    override fun onAnimationStart(animation: Animator) {
        onAnimationStart(animation)
    }

    override fun onAnimationEnd(animation: Animator) {
        onAnimationEnd(animation)
    }

    override fun onAnimationCancel(animation: Animator) {
        onAnimationCancel(animation)
    }

    override fun onAnimationRepeat(animation: Animator) {
        onAnimationRepeat(animation)
    }
})