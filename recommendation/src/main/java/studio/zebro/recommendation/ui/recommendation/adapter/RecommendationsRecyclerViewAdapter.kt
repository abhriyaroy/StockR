package studio.zebro.recommendation.ui.recommendation.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import studio.zebro.recommendation.databinding.ItemRecommendationBinding
import studio.zebro.recommendation.domain.model.StockRecommendationModel
import studio.zebro.recommendation.ui.transition.RecommendationItemToRecommendationDetailTransitionModel

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
            if(position == 4){
                binding.itemNameTextView.transitionName = "checker_1"
            } else {
                binding.itemNameTextView.transitionName = "$position"
            }
//            binding.itemNameTextView.transitionName =
//            "${stockRecommendationModel.codeNumber}$position${binding.itemNameTextView.id}"
//            binding.itemSellAtTextView.transitionName =
//                "${stockRecommendationModel.codeNumber}$position${binding.itemSellAtTextView.id}"
//            binding.itemBuyAtTextView.transitionName =
//                "${stockRecommendationModel.codeNumber}$position${binding.itemBuyAtTextView.id}"
//            binding.itemActionTextView.transitionName =
//                "${stockRecommendationModel.codeNumber}$position${binding.itemActionTextView.id}"
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
//                    RecommendationItemToRecommendationDetailTransitionModel(
//                        binding.itemRootViewGroup,
//                        binding.itemRootViewGroup.transitionName,
//                        binding.itemNameTextView,
//                        binding.itemNameTextView.transitionName,
//                        binding.itemBuyAtTextView,
//                        binding.itemBuyAtTextView.transitionName,
//                        binding.itemSellAtTextView,
//                        binding.itemSellAtTextView.transitionName,
//                        binding.itemActionTextView,
//                        binding.itemActionTextView.transitionName,
//                    )
                binding.itemNameTextView
                )
            }
        }
    }
}