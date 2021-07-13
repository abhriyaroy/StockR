package studio.zebro.research.testdataprovider

import studio.zebro.datasource.model.NiftyIndexesDayWiseDataModel
import java.util.*

object NiftyIndexesDayWiseDataModelDataProvider {

    fun getNiftyIndexesDayWiseDataModel() = NiftyIndexesDayWiseDataModel(
        UUID.randomUUID().toString(),
        Random().nextFloat(),
        Random().nextFloat().toString(),
        Random().nextFloat().toString(),
        false
    )
}