package studio.zebro.recommendation.ui.recommendation.adapter

import studio.zebro.recommendation.domain.model.StockRecommendationModel
import studio.zebro.recommendation.ui.transition.RecommendationRecyclerItemTransitionViewsModel

interface RecommendationItemClickListener {
    fun onRecommendationItemClick(
        position: Int,
        stockRecommendationModel: StockRecommendationModel,
        viewsModel: RecommendationRecyclerItemTransitionViewsModel
    )
}