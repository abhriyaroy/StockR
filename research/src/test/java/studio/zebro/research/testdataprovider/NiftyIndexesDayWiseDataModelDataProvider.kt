package studio.zebro.research.testdataprovider

import studio.zebro.datasource.model.NiftyIndexesDayWiseDataModel
import studio.zebro.research.data.entity.NiftyIndexesDayEntity
import java.util.*

object NiftyIndexesDayWiseDataModelDataProvider {

    fun getNiftyIndexesDayWiseDataModel() = NiftyIndexesDayWiseDataModel(
        UUID.randomUUID().toString(),
        Random().nextFloat(),
        Random().nextFloat().toString(),
        Random().nextFloat().toString(),
        false
    )

    fun getNiftyIndexesDayEntity() = NiftyIndexesDayEntity(
        UUID.randomUUID().toString(),
        Random().nextFloat(),
        Random().nextFloat().toString(),
        Random().nextFloat().toString(),
        false
    )
}