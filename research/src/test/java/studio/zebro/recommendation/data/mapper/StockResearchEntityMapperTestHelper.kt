package studio.zebro.recommendation.data.mapper

import studio.zebro.datasource.model.StockResearchDataModel
import studio.zebro.research.data.entity.StockResearchEntity

object StockResearchEntityMapperTestHelper {

    fun mapStockResearchRemoteDataModelToStockResearchEntity(
        stockResearchDataModel: StockResearchDataModel
    ): StockResearchEntity {
        return StockResearchEntity(
            stockResearchDataModel.fullName,
            stockResearchDataModel.shortName,
            stockResearchDataModel.codeNumber,
            stockResearchDataModel.updatedAt,
            stockResearchDataModel.entryPriceInRupees,
            stockResearchDataModel.targetPriceInRupees,
            stockResearchDataModel.stopLossPriceInRupees,
            stockResearchDataModel.upsidePercentage,
            stockResearchDataModel.duration,
            stockResearchDataModel.type,
            stockResearchDataModel.action,
            stockResearchDataModel.remark,
        )
    }
}