package studio.zebro.research.testdataprovider

import studio.zebro.datasource.model.StockResearchDataModel
import studio.zebro.research.data.entity.StockResearchEntity
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

    fun getStockResearchEntity(action : String = "BUY") = StockResearchEntity(
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        Random().nextInt().toString(),
        Random().nextInt().toString(),
        Random().nextFloat().toString().replace('.', ','),
        Random().nextFloat().toString().replace('.', ','),
        Random().nextFloat().toString().replace('.', ','),
        Random().nextFloat().toString().replace('.', ','),
        Random().nextInt().toString(),
        Random().nextInt().toString(),
        action,
        UUID.randomUUID().toString()
    )

    fun getStockResearchEntityList() = listOf(
        getStockResearchEntity(),
        getStockResearchEntity(),
        getStockResearchEntity(),
        getStockResearchEntity("SELL")
    )
}