package studio.zebro.research.uicompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import studio.zebro.core.theme.*
import studio.zebro.core.util.withDelayOnMain
import studio.zebro.datasource.util.ResourceState
import studio.zebro.research.R
import studio.zebro.research.domain.model.NiftyIndexesDayModel
import studio.zebro.research.ui.research.ResearchViewModel
import java.util.concurrent.TimeUnit.*

@Composable
fun ResearchListingScreen(
    navController: NavController,
    researchViewModel: ResearchViewModel
) {

    println("hererererere researchh--->>>>> ${researchViewModel.hashCode()}")

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
            if (nifty50Index.value is ResourceState.Success) {
                NiftyIndexCard(
                    false,
                    (nifty50Index.value as ResourceState.Success<NiftyIndexesDayModel>).data
                )
            } else {
                NiftyIndexCard(true)
            }
        }
    }

    nifty50Index.value?.apply {
        when (this) {
            is ResourceState.Success -> {
                println("this success")
                refreshNifty50IndexAfterDelay(researchViewModel)
            }
            is ResourceState.Loading -> {
                println("this loading")
            }
            is ResourceState.Error -> {
                println("this error")
                refreshNifty50IndexAfterDelay(researchViewModel)
            }
        }
    }
}

private fun handleNifty50IndexSuccessState(resourceState: ResourceState.Success<NiftyIndexesDayModel>) {
//        binding.indexValueTextView.text = resourceState.data.value.toString()
//        binding.indexChangeValueTextView.text = resourceState.data.changeValue
//        binding.indexChangePercentageTextView.text = resourceState.data.changePercentage
//        binding.indexChangeImageView.apply {
//            if (resourceState.data.isPositiveChange) {
//                setImageDrawable(
//                    ContextCompat.getDrawable(
//                        requireContext(),
//                        R.drawable.ic_arrow_drop_up
//                    )!!.mutate().apply {
//                        setTint(ContextCompat.getColor(requireContext(), R.color.positive))
//                    })
//            } else {
//                setImageDrawable(
//                    ContextCompat.getDrawable(
//                        requireContext(),
//                        R.drawable.ic_arrow_drop_down
//                    )!!.mutate().apply {
//                        setTint(ContextCompat.getColor(requireContext(), R.color.negative))
//                    })
//            }
//            binding.indexChangeValueTextView.setTextColor(
//                CoreUtility.getStockUpOrDownColor(
//                    requireContext(),
//                    resourceState.data.isPositiveChange
//                )
//            )
//            binding.indexChangePercentageTextView.setTextColor(
//                CoreUtility.getStockUpOrDownColor(
//                    requireContext(),
//                    resourceState.data.isPositiveChange
//                )
//            )
//        }
//        binding.niftyIndexCardProgressView.gone()
//        refreshNifty50IndexAfterDelay()
}

private fun handleNifty50IndexLoadingState() {
//        if (researchViewModel.nifty50IndexData.value == null) {
//            binding.niftyIndexCardProgressView.visible()
//        }
}

private fun refreshNifty50IndexAfterDelay(researchViewModel: ResearchViewModel) {
    withDelayOnMain(MINUTES.toMillis(5)) {
        researchViewModel.getNifty50IndexData()
    }
}