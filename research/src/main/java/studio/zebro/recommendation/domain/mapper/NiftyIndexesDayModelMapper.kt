package studio.zebro.recommendation.domain.mapper

import studio.zebro.recommendation.data.entity.NiftyIndexesDayEntity
import studio.zebro.recommendation.domain.model.NiftyIndexesDayModel

object NiftyIndexesDayModelMapper {
    fun mapNiftyIndexesDayEntityToNiftyIndexesDayModel(niftyIndexesDayEntity: NiftyIndexesDayEntity): NiftyIndexesDayModel {
        return NiftyIndexesDayModel(
            niftyIndexesDayEntity.name,
            niftyIndexesDayEntity.value,
            niftyIndexesDayEntity.changePercentage,
            niftyIndexesDayEntity.changeValue,
            niftyIndexesDayEntity.isPositiveChange,
        )
    }

    fun mapNiftyIndexesDayModelToNiftyIndexesDayEntity(niftyIndexesDayModel: NiftyIndexesDayModel): NiftyIndexesDayEntity {
        return NiftyIndexesDayEntity(
            niftyIndexesDayModel.name,
            niftyIndexesDayModel.value,
            niftyIndexesDayModel.changePercentage,
            niftyIndexesDayModel.changeValue,
            niftyIndexesDayModel.isPositiveChange,
        )
    }
}