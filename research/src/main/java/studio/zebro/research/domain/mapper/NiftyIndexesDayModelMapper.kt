package studio.zebro.research.domain.mapper

import studio.zebro.research.data.entity.NiftyIndexesDayEntity
import studio.zebro.research.domain.model.NiftyIndexesDayModel

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