package studio.zebro.research.data.mapper

import studio.zebro.datasource.model.StockResearchsDataModel
import studio.zebro.research.data.entity.StockResearchEntity

object StockResearchEntityMapper {
    fun mapStockResearchEntityToStockResearchRemoteDataModel(stockResearchEntity: StockResearchEntity): StockResearchsDataModel {
        return StockResearchsDataModel(
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
        stockResearchsDataModel: StockResearchsDataModel
    ): StockResearchEntity {
        return StockResearchEntity(
            stockResearchsDataModel.fullName,
            stockResearchsDataModel.shortName,
            stockResearchsDataModel.codeNumber,
            stockResearchsDataModel.updatedAt,
            stockResearchsDataModel.entryPriceInRupees,
            stockResearchsDataModel.targetPriceInRupees,
            stockResearchsDataModel.stopLossPriceInRupees,
            stockResearchsDataModel.upsidePercentage,
            stockResearchsDataModel.duration,
            stockResearchsDataModel.type,
            stockResearchsDataModel.action,
            stockResearchsDataModel.remark,
        )
    }
}