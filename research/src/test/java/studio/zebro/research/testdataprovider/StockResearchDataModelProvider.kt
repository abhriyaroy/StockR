package studio.zebro.research.testdataprovider

import studio.zebro.datasource.model.StockResearchDataModel
import java.util.*

object StockResearchDataModelProvider {

    fun getStockResearchDataModel(action : String = "BUY") = StockResearchDataModel(
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        Random().nextInt().toString(),
        Random().nextInt().toString(),
        Random().nextInt().toString(),
        Random().nextInt().toString(),
        Random().nextInt().toString(),
        Random().nextInt().toString(),
        Random().nextInt().toString(),
        Random().nextInt().toString(),
        action,
        UUID.randomUUID().toString()
    )

    fun getStockResearchDataModelList() = listOf(
        getStockResearchDataModel(),
        getStockResearchDataModel(),
        getStockResearchDataModel(),
        getStockResearchDataModel("SELL")
    )
}