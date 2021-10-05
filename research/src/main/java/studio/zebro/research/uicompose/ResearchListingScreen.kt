package studio.zebro.research.uicompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import studio.zebro.core.theme.*
import studio.zebro.core.util.CoreUtility
import studio.zebro.core.util.gone
import studio.zebro.core.util.visible
import studio.zebro.core.util.withDelayOnMain
import studio.zebro.datasource.util.ResourceState
import studio.zebro.research.R
import studio.zebro.research.domain.model.NiftyIndexesDayModel
import studio.zebro.research.ui.research.ResearchViewModel

@Composable
fun ResearchListingScreen(
    navController: NavController,
    researchViewModel: ResearchViewModel = viewModel()
) {
    val nifty50Index = researchViewModel.nifty50IndexData.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryMainColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_stockr),
                contentDescription = null,
                modifier = Modifier
                    .width(dimensionResource(R.dimen.dimen_96dp))
                    .height(dimensionResource(R.dimen.dimen_48dp)),
                contentScale = ContentScale.Crop
            )
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
                            painter = painterResource(R.drawable.ic_arrow_drop_up),
                            contentDescription = null,
                            modifier = Modifier
                                .width(dimensionResource(R.dimen.dimen_24dp))
                                .height(dimensionResource(R.dimen.dimen_24dp)),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = stringResource(R.string.index_value),
                            textAlign = TextAlign.Start,
                            color = WhiteColor,
                            style = Typography.h2
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = stringResource(R.string.index_change_percentage),
                                textAlign = TextAlign.Start,
                                color = WhiteColor,
                                style = Typography.subtitle1
                            )
                            Text(
                                text = stringResource(R.string.index_change_value),
                                textAlign = TextAlign.Start,
                                color = WhiteColor,
                                style = Typography.subtitle1
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

    nifty50Index.value?.apply {
        when (this) {
            is ResourceState.Success -> {
                handleNifty50IndexSuccessState(this)
            }
            is ResourceState.Loading -> {
                handleNifty50IndexLoadingState()
            }
            is ResourceState.Error -> {
                refreshNifty50IndexAfterDelay()
            }
        }
    }

    fun handleNifty50IndexSuccessState(resourceState: ResourceState.Success<NiftyIndexesDayModel>) {
        binding.indexValueTextView.text = resourceState.data.value.toString()
        binding.indexChangeValueTextView.text = resourceState.data.changeValue
        binding.indexChangePercentageTextView.text = resourceState.data.changePercentage
        binding.indexChangeImageView.apply {
            if (resourceState.data.isPositiveChange) {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_arrow_drop_up
                    )!!.mutate().apply {
                        setTint(ContextCompat.getColor(requireContext(), R.color.positive))
                    })
            } else {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_arrow_drop_down
                    )!!.mutate().apply {
                        setTint(ContextCompat.getColor(requireContext(), R.color.negative))
                    })
            }
            binding.indexChangeValueTextView.setTextColor(
                CoreUtility.getStockUpOrDownColor(
                    requireContext(),
                    resourceState.data.isPositiveChange
                )
            )
            binding.indexChangePercentageTextView.setTextColor(
                CoreUtility.getStockUpOrDownColor(
                    requireContext(),
                    resourceState.data.isPositiveChange
                )
            )
        }
        binding.niftyIndexCardProgressView.gone()
        refreshNifty50IndexAfterDelay()
    }

    fun handleNifty50IndexLoadingState() {
        if (researchViewModel.nifty50IndexData.value == null) {
            binding.niftyIndexCardProgressView.visible()
        }
    }

    fun refreshNifty50IndexAfterDelay() {
        withDelayOnMain(5000) {
            researchViewModel.getNifty50IndexData()
        }
    }
}