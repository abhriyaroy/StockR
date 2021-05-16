package studio.zebro.recommendation.ui.transition

import android.os.Parcel
import android.os.Parcelable
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView

data class RecommendationItemToRecommendationDetailTransitionModel(
    val rootItem: ViewGroup?,
    val rootItemTransitionName: String,
    val titleTextView: AppCompatTextView?,
    val titleTextViewTransitionName: String,
    val sellAtTextView: AppCompatTextView?,
    val sellAtTextViewTransitionName: String,
    val buyAtTextView: AppCompatTextView?,
    val buyAtTextViewTransitionName: String,
    val actionTextView: AppCompatTextView?,
    val actionTextViewTransitionName: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        null,
        parcel.readString()!!,
        null,
        parcel.readString()!!,
        null,
        parcel.readString()!!,
        null,
        parcel.readString()!!,
        null,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(rootItemTransitionName)
        parcel.writeString(titleTextViewTransitionName)
        parcel.writeString(sellAtTextViewTransitionName)
        parcel.writeString(buyAtTextViewTransitionName)
        parcel.writeString(actionTextViewTransitionName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR :
        Parcelable.Creator<RecommendationItemToRecommendationDetailTransitionModel> {
        override fun createFromParcel(parcel: Parcel): RecommendationItemToRecommendationDetailTransitionModel {
            return RecommendationItemToRecommendationDetailTransitionModel(parcel)
        }

        override fun newArray(size: Int): Array<RecommendationItemToRecommendationDetailTransitionModel?> {
            return arrayOfNulls(size)
        }
    }

}