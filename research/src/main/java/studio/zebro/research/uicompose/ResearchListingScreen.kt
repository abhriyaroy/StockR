package studio.zebro.research.uicompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import studio.zebro.core.theme.*
import studio.zebro.core.util.CoreUtility.getStockUpOrDownColorComposable
import studio.zebro.core.util.withDelayOnMain
import studio.zebro.datasource.util.ResourceState
import studio.zebro.research.R
import studio.zebro.research.domain.model.NiftyIndexesDayModel
import studio.zebro.research.domain.model.StockResearchModel
import studio.zebro.research.ui.research.ResearchViewModel
import java.util.concurrent.TimeUnit.*


@Composable
fun ResearchListingScreen(
    navController: NavController,
    researchViewModel: ResearchViewModel
) {
    val nifty50Index = researchViewModel.nifty50IndexData.observeAsState()
    val researchListState = researchViewModel.stockResearch.observeAsState()
    var researchList = remember {
        mutableStateOf(listOf<StockResearchModel>())
    }


    println(researchListState.value)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryMainColor)
    ) {
        Column {
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

            Card(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(1f)
                    .padding(16.dp),
                elevation = 10.dp,
                backgroundColor = PrimaryLightColor,
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(R.string.research),
                        textAlign = TextAlign.Start,
                        color = WhiteColor,
                        style = Typography.h4
                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(researchList.value) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Divider(color = WhiteColor, thickness = 1.dp)
                            Spacer(modifier = Modifier.height(8.dp))
                            println("the item in list $it")
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = it.shortName,
                                        textAlign = TextAlign.Start,
                                        color = getStockUpOrDownColorComposable(it.action),
                                        style = Typography.h4
                                    )
                                    Text(
                                        text = stringResource(
                                            R.string.buy_at_x,
                                            it.entryPriceInRupees
                                        ),
                                        textAlign = TextAlign.Start,
                                        color = WhiteColor,
                                        style = Typography.h4
                                    )
                                    Text(
                                        text = stringResource(
                                            R.string.sell_at_x,
                                            it.targetPriceInRupees
                                        ),
                                        textAlign = TextAlign.Start,
                                        color = WhiteColor,
                                        style = Typography.h4
                                    )
                                }
                                Text(
                                    text = buildAnnotatedString {
                                        append(stringResource(R.string.action))
                                        withStyle(
                                            style = SpanStyle(
                                                getStockUpOrDownColorComposable(
                                                    it.action
                                                )
                                            )
                                        ) {
                                            append(it.action)
                                        }
                                    },
                                    textAlign = TextAlign.Start,
                                    color = WhiteColor,
                                    style = Typography.h4
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    nifty50Index.value?.apply {
        when (this) {
            is ResourceState.Success -> {
                refreshNifty50IndexAfterDelay(researchViewModel)
            }
            is ResourceState.Loading -> {
            }
            is ResourceState.Error -> {
                refreshNifty50IndexAfterDelay(researchViewModel, 0)
            }
        }
    }

    researchListState.value?.apply {
        when (this) {
            is ResourceState.Success -> {
                researchList.value = data
                println("data loaded $researchList")
            }
            is ResourceState.Loading -> {
            }
            is ResourceState.Error -> {
                researchViewModel.getStockResearch(true)
            }
        }
    }
}

private fun refreshNifty50IndexAfterDelay(
    researchViewModel: ResearchViewModel,
    timeToRefresh: Long = 5L
) {
    withDelayOnMain(MINUTES.toMillis(timeToRefresh)) {
        researchViewModel.getNifty50IndexData()
    }
}
