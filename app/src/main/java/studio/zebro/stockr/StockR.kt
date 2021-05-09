package studio.zebro.stockr

import android.app.Application
import com.chibatching.kotpref.Kotpref
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StockR : Application(){

    override fun onCreate() {
        super.onCreate()
        Kotpref.init(this)
    }

}