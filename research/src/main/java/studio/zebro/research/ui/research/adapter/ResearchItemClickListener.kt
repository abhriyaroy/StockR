package studio.zebro.research.ui.research.adapter

import studio.zebro.research.domain.model.StockResearchModel
import studio.zebro.research.ui.transition.ResearchRecyclerItemTransitionViewsModel

interface ResearchItemClickListener {
    fun onResearchItemClick(
        position: Int,
        stockResearchModel: StockResearchModel,
        viewsModel: ResearchRecyclerItemTransitionViewsModel
    )
}