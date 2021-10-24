package studio.zebro.research.uicompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import studio.zebro.core.theme.*
import studio.zebro.research.R
import studio.zebro.research.domain.model.NiftyIndexesDayModel

@Composable
fun NiftyIndexCard(shouldShowProgressBar: Boolean, data: NiftyIndexesDayModel? = null) {
    Card(
        modifier = Modifier
            .padding(16.dp, 8.dp),
        elevation = 10.dp,
        backgroundColor = PrimaryLightColor,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(R.string.nifty_50),
                    textAlign = TextAlign.Start,
                    color = WhiteColor,
                    modifier = Modifier.width(50.dp),
                    style = Typography.h4
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(
                        if (data?.isPositiveChange == true) {
                            R.drawable.ic_arrow_drop_up
                        } else {
                            R.drawable.ic_arrow_drop_down
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.dimen_24dp))
                        .height(dimensionResource(R.dimen.dimen_24dp))
                        .alpha(getAlphaIfShouldHideView(!shouldShowProgressBar)),
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(getIndexColor(data?.isPositiveChange ?: false))
                )
                Text(
                    modifier = Modifier.alpha(getAlphaIfShouldHideView(!shouldShowProgressBar)),
                    text = stringResource(R.string.index_value),
                    textAlign = TextAlign.Start,
                    color = WhiteColor,
                    style = Typography.h2
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.alpha(getAlphaIfShouldHideView(!shouldShowProgressBar))) {
                    Text(
                        text = data?.changePercentage ?: "",
                        textAlign = TextAlign.Start,
                        color = getIndexColor(data?.isPositiveChange ?: false),
                        style = Typography.subtitle1,
                    )
                    Text(
                        text = data?.changeValue ?: "",
                        textAlign = TextAlign.Start,
                        color = getIndexColor(data?.isPositiveChange ?: false),
                        style = Typography.subtitle1
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        if (shouldShowProgressBar) {
            CircularProgressIndicator(color = WhiteColor)
        }
    }
}

private fun getAlphaIfShouldHideView(shouldHideView: Boolean) = if (shouldHideView) 1f else 0f

private fun getIndexColor(isPositiveChange: Boolean) =
    if (isPositiveChange) PositiveColor else NegativeColor