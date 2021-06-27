package studio.zebro.research.ui.research.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import studio.zebro.research.databinding.ItemResearchBinding
import studio.zebro.research.domain.model.StockResearchModel
import studio.zebro.research.ui.transition.ResearchRecyclerItemTransitionViewsModel

class ResearchRecyclerViewAdapter(
    val context: Context,
    private val researchItemClickListener: ResearchItemClickListener
) : RecyclerView.Adapter<ResearchRecyclerViewAdapter.ResearchRecyclerViewHolder>() {

    private var itemsList: List<StockResearchModel> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResearchRecyclerViewHolder {
        return ItemResearchBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ).let {
            ResearchRecyclerViewHolder(it)
        }
    }

    override fun onBindViewHolder(holder: ResearchRecyclerViewHolder, position: Int) {
        with(itemsList[position]) {
            holder.decorateItem(this)
            holder.configureItemForTransition(position, this)
            holder.attachClickListener(position, this, researchItemClickListener)
        }

    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun setItemsList(list: List<StockResearchModel>) {
        itemsList = list
        this.notifyDataSetChanged()
    }

    fun getVisibleItemsList() = itemsList

    class ResearchRecyclerViewHolder(
        private val binding: ItemResearchBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun decorateItem(stockResearchModel: StockResearchModel) {
            binding.stockResearchModelItem = stockResearchModel
            binding.executePendingBindings()
        }

        fun configureItemForTransition(
            position: Int,
            stockResearchModel: StockResearchModel
        ) {
            binding.itemRootViewGroup.transitionName = "${position}viewgroup"
            binding.itemNameTextView.transitionName = "${position}titletextview"
            binding.itemSellAtTextView.transitionName = "${position}selltextview"
            binding.itemBuyAtTextView.transitionName = "${position}buytextview"
            binding.itemActionTextView.transitionName = "${position}actiontextview"
        }

        fun attachClickListener(
            position: Int,
            stockResearchModel: StockResearchModel,
            researchItemClickListener: ResearchItemClickListener
        ) {
            binding.itemRootViewGroup.setOnClickListener {
                researchItemClickListener.onResearchItemClick(
                    position,
                    stockResearchModel,
                    ResearchRecyclerItemTransitionViewsModel(
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