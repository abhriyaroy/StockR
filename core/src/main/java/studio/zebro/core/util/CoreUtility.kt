package studio.zebro.core.util

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import androidx.navigation.NavOptions
import studio.zebro.core.R

object CoreUtility {

    fun getDefaultNavigationAnimation() = NavOptions.Builder().apply {
        this.setEnterAnim(R.anim.slide_in_right).setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left).setPopExitAnim(R.anim.slide_out_right)
    }.build()

    fun replaceDelimiterFromNumberIfAnyStringAndReturnFloat(
        originalNumber: String,
        delimiter: String = ","
    ): Float {
        return if (originalNumber.contains(delimiter)) {
            originalNumber.replace(delimiter, "").toFloat()
        } else {
            originalNumber.toFloat()
        }
    }

    fun getStockUpOrDownColor(context: Context, action: String): Int {
        if (action.equals("buy", true) || action.equals("add", true)) {
            return ContextCompat.getColor(context, R.color.positive)
        } else {
            return ContextCompat.getColor(context, R.color.negative)
        }
    }

    fun getActionSpannableHighlight(context: Context, string: String, action: String): SpannableString {
        with(SpannableString(string)){
            val indexOfSeparator = this.indexOf(':')
            setSpan(
                ForegroundColorSpan(ContextCompat.getColor(context, R.color.white)),
                0, indexOfSeparator+1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            setSpan(
                ForegroundColorSpan(getStockUpOrDownColor(context, action)),
                indexOfSeparator+1, length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            return this
        }
    }

}