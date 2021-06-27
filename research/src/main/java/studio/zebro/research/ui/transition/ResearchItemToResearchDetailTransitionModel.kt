package studio.zebro.research.ui.transition

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView

data class ResearchRecyclerItemTransitionViewsModel(
    val rootItem: ViewGroup,
    val titleTextView: AppCompatTextView,
    val sellAtTextView: AppCompatTextView,
    val buyAtTextView: AppCompatTextView,
    val actionTextView: AppCompatTextView
)