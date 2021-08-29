package studio.zebro.stockr

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import studio.zebro.stockr.R
import studio.zebro.stockr.uicompose.SplashScreen
import studio.zebro.stockr.uicompose.theme.StockRTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StockRTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    SplashScreen()
                }
            }
        }
    }
}