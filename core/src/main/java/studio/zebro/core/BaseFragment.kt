package studio.zebro.core

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater

abstract class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSharedElementTransition()
    }

    private fun setupSharedElementTransition() {
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        ).apply {
            duration = 2000
        }
    }
}