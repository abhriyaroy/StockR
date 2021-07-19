package studio.zebro.research.domain.mapper

import studio.zebro.research.data.entity.StockResearchEntity
import studio.zebro.research.domain.model.StockResearchModel

class StockResearchModelMapperTestHelper {
    fun mapStockResearchEntityToStockResearchModel(stockResearchEntity: StockResearchEntity): StockResearchModel {
        return StockResearchModel(
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

    fun mapStockResearchModelToStockResearchEntity(stockResearchModel: StockResearchModel): StockResearchEntity {
        return StockResearchEntity(
            stockResearchModel.fullName,
            stockResearchModel.shortName,
            stockResearchModel.codeNumber,
            stockResearchModel.updatedAt,
            stockResearchModel.entryPriceInRupees,
            stockResearchModel.targetPriceInRupees,
            stockResearchModel.stopLossPriceInRupees,
            stockResearchModel.upsidePercentage,
            stockResearchModel.duration,
            stockResearchModel.type,
            stockResearchModel.action,
            stockResearchModel.remark,
        )
    }
}