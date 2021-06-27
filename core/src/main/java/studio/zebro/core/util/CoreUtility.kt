package studio.zebro.core.util

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import androidx.navigation.NavOptions
import studio.zebro.core.R
import studio.zebro.datasource.model.StockResearchDataModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

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
        return if (action.equals("buy", true) || action.equals("add", true)) {
            ContextCompat.getColor(context, R.color.positive)
        } else {
            ContextCompat.getColor(context, R.color.negative)
        }
    }

    fun getStockUpOrDownColor(context: Context, isPositive: Boolean): Int {
        return if (isPositive) {
            ContextCompat.getColor(context, R.color.positive)
        } else {
            ContextCompat.getColor(context, R.color.negative)
        }
    }

    fun getStockPositiveNegativeState(action: String) :StockPositiveNegativeState{
        return if (action.equals("buy", true) || action.equals("add", true)) {
            StockPositiveNegativeState.POSITVE
        } else {
            StockPositiveNegativeState.NEGATIVE
        }
    }

    fun getActionSpannableHighlight(
        context: Context,
        string: String,
        action: String
    ): SpannableString {
        with(SpannableString(string)) {
            val indexOfSeparator = this.indexOf(':')
            setSpan(
                ForegroundColorSpan(ContextCompat.getColor(context, R.color.white)),
                0, indexOfSeparator + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            setSpan(
                ForegroundColorSpan(getStockUpOrDownColor(context, action)),
                indexOfSeparator + 1, length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            return this
        }
    }

    fun formatDate(inputDate: String, inputDatFormat : String, outputDateFormat: String): String {
        val inputFormat: DateFormat = SimpleDateFormat(inputDatFormat, Locale.ENGLISH)
        val outputFormat: DateFormat = SimpleDateFormat(outputDateFormat, Locale.ENGLISH)

        val date: Date = inputFormat.parse(inputDate)
        return outputFormat.format(date)
    }

    const val DD_MMM_YYYY_DATE_FORMAT = "dd-MMM-yyyy"
    const val DD_MM_YY_DATE_FORMAT = "dd-MM-yy"
}