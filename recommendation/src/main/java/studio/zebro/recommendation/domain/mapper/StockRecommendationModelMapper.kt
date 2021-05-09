package studio.zebro.recommendation.domain.mapper

import studio.zebro.recommendation.data.entity.StockRecommendationEntity
import studio.zebro.recommendation.domain.model.StockRecommendationModel

object StockRecommendationModelMapper {
    fun mapStockRecommendationEntityToStockRecommendationModel(stockRecommendationEntity: StockRecommendationEntity): StockRecommendationModel {
        return StockRecommendationModel(
            stockRecommendationEntity.fullName,
            stockRecommendationEntity.shortName,
            stockRecommendationEntity.codeNumber,
            stockRecommendationEntity.updatedAt,
            stockRecommendationEntity.entryPriceInRupees,
            stockRecommendationEntity.targetPriceInRupees,
            stockRecommendationEntity.stopLossPriceInRupees,
            stockRecommendationEntity.upsidePercentage,
            stockRecommendationEntity.duration,
            stockRecommendationEntity.type,
            stockRecommendationEntity.action,
            stockRecommendationEntity.remark
        )
    }

    fun mapStockRecommendationModelToStockRecommendationEntity(stockRecommendationModel: StockRecommendationModel): StockRecommendationEntity {
        return StockRecommendationEntity(
            stockRecommendationModel.fullName,
            stockRecommendationModel.shortName,
            stockRecommendationModel.codeNumber,
            stockRecommendationModel.updatedAt,
            stockRecommendationModel.entryPriceInRupees,
            stockRecommendationModel.targetPriceInRupees,
            stockRecommendationModel.stopLossPriceInRupees,
            stockRecommendationModel.upsidePercentage,
            stockRecommendationModel.duration,
            stockRecommendationModel.type,
            stockRecommendationModel.action,
            stockRecommendationModel.remark,
        )
    }
}