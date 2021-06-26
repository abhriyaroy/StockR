package studio.zebro.recommendation.ui.transition

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView

data class RecommendationRecyclerItemTransitionViewsModel(
    val rootItem: ViewGroup,
    val titleTextView: AppCompatTextView,
    val sellAtTextView: AppCompatTextView,
    val buyAtTextView: AppCompatTextView,
    val actionTextView: AppCompatTextView
)