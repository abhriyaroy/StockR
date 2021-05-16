package studio.zebro.datasource.local

import studio.zebro.datasource.model.ErrorModel
import java.lang.Exception

class CustomError(val errorModel: ErrorModel?) : Exception()