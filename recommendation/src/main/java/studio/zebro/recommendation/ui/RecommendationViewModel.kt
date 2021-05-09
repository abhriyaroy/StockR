package studio.zebro.recommendation.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import studio.zebro.recommendation.domain.RecommendationUseCase

class RecommendationViewModel @ViewModelInject
constructor(private val recommendationUseCase: RecommendationUseCase) : ViewModel() {
    // TODO: Implement the ViewModel
}