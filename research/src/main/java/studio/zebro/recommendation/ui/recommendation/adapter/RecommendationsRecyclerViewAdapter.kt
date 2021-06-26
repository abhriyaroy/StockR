package studio.zebro.recommendation.ui.recommendation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import studio.zebro.recommendation.databinding.ItemRecommendationBinding
import studio.zebro.recommendation.domain.model.StockRecommendationModel
import studio.zebro.recommendation.ui.transition.RecommendationRecyclerItemTransitionViewsModel

class RecommendationsRecyclerViewAdapter(
    val context: Context,
    private val recommendationItemClickListener: RecommendationItemClickListener
) : RecyclerView.Adapter<RecommendationsRecyclerViewAdapter.RecommendationsRecyclerViewHolder>() {

    private var itemsList: List<StockRecommendationModel> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendationsRecyclerViewHolder {
        return ItemRecommendationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ).let {
            RecommendationsRecyclerViewHolder(it)
        }
    }

    override fun onBindViewHolder(holder: RecommendationsRecyclerViewHolder, position: Int) {
        with(itemsList[position]) {
            holder.decorateItem(this)
            holder.configureItemForTransition(position, this)
            holder.attachClickListener(position, this, recommendationItemClickListener)
        }

    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun setItemsList(list: List<StockRecommendationModel>) {
        itemsList = list
        this.notifyDataSetChanged()
    }

    fun getVisibleItemsList() = itemsList

    class RecommendationsRecyclerViewHolder(
        private val binding: ItemRecommendationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun decorateItem(stockRecommendationModel: StockRecommendationModel) {
            binding.stockRecommendationModelItem = stockRecommendationModel
            binding.executePendingBindings()
        }

        fun configureItemForTransition(
            position: Int,
            stockRecommendationModel: StockRecommendationModel
        ) {
            binding.itemRootViewGroup.transitionName = "${position}viewgroup"
            binding.itemNameTextView.transitionName = "${position}titletextview"
            binding.itemSellAtTextView.transitionName = "${position}selltextview"
            binding.itemBuyAtTextView.transitionName = "${position}buytextview"
            binding.itemActionTextView.transitionName = "${position}actiontextview"
        }

        fun attachClickListener(
            position: Int,
            stockRecommendationModel: StockRecommendationModel,
            recommendationItemClickListener: RecommendationItemClickListener
        ) {
            binding.itemRootViewGroup.setOnClickListener {
                recommendationItemClickListener.onRecommendationItemClick(
                    position,
                    stockRecommendationModel,
                    RecommendationRecyclerItemTransitionViewsModel(
                        binding.itemRootViewGroup,
                        binding.itemNameTextView,
                        binding.itemBuyAtTextView,
                        binding.itemSellAtTextView,
                        binding.itemActionTextView,
                    )
                )
            }
        }
    }
}