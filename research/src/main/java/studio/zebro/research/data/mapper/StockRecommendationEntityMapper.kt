package studio.zebro.research.data.mapper

import studio.zebro.datasource.model.StockResearchDataModel
import studio.zebro.research.data.entity.StockResearchEntity

object StockResearchEntityMapper {
    fun mapStockResearchEntityToStockResearchRemoteDataModel(stockResearchEntity: StockResearchEntity): StockResearchDataModel {
        return StockResearchDataModel(
            stockResearchEntity.fullName,
            stockResearchEntity.shortName,
            stockResearchEntity.codeNumber,
            stockResearchEntity.updatedAt,
            stockResearchEntity.entryPriceInRupees,
            stockResearchEntity.targetPriceInRupees,
            stockResearchEntity.stopLossPriceInRupees,
            stockResearchEntity.upsidePercentage,
            stockResearchEntity.duration,
            stockResearchEntity.type,
            stockResearchEntity.action,
            stockResearchEntity.remark
        )
    }

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