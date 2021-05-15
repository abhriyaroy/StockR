package studio.zebro.recommendation.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import studio.zebro.recommendation.databinding.ItemRecommendationBinding
import studio.zebro.recommendation.domain.model.StockRecommendationModel

class RecommendationsRecyclerViewAdapter(
    var context: Context
) : RecyclerView.Adapter<RecommendationsRecyclerViewAdapter.RecommendationsRecyclerViewHolder>() {

    private var itemsList : List<StockRecommendationModel> = listOf()

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
        holder.decorateItem(itemsList[position])
    }

    override fun getItemCount(): Int {
        Log.d(this.javaClass.name, "the list is $itemsList")
        return itemsList.size
    }

    fun setItemsList(list: List<StockRecommendationModel>) {
        itemsList = list
        Log.d(this.javaClass.name, "set items lise $itemsList")
        this.notifyDataSetChanged()
    }

    class RecommendationsRecyclerViewHolder(
        private val binding: ItemRecommendationBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun decorateItem(stockRecommendationModel: StockRecommendationModel) {
            binding.stockRecommendationModelItem = stockRecommendationModel
            binding.executePendingBindings()
        }
    }
}