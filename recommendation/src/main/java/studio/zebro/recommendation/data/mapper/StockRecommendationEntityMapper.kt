package studio.zebro.recommendation.data.mapper

import studio.zebro.datasource.model.StockRecommendationsDataModel
import studio.zebro.recommendation.data.entity.StockRecommendationEntity

object StockRecommendationEntityMapper {
    fun mapStockRecommendationEntityToStockRecommendationRemoteDataModel(stockRecommendationEntity: StockRecommendationEntity): StockRecommendationsDataModel {
        return StockRecommendationsDataModel(
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

    fun mapStockRecommendationRemoteDataModelToStockRecommendationEntity(
        stockRecommendationsDataModel: StockRecommendationsDataModel
    ): StockRecommendationEntity {
        return StockRecommendationEntity(
            stockRecommendationsDataModel.fullName,
            stockRecommendationsDataModel.shortName,
            stockRecommendationsDataModel.codeNumber,
            stockRecommendationsDataModel.updatedAt,
            stockRecommendationsDataModel.entryPriceInRupees,
            stockRecommendationsDataModel.targetPriceInRupees,
            stockRecommendationsDataModel.stopLossPriceInRupees,
            stockRecommendationsDataModel.upsidePercentage,
            stockRecommendationsDataModel.duration,
            stockRecommendationsDataModel.type,
            stockRecommendationsDataModel.action,
            stockRecommendationsDataModel.remark,
        )
    }
}