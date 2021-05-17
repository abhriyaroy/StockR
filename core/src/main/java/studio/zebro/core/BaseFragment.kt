package studio.zebro.core

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.transition.MaterialContainerTransform

abstract class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSharedElementTransition()
    }

    private fun setupSharedElementTransition() {
        sharedElementEnterTransition = androidx.transition.TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        ).apply {
            duration = 700
        }
    }
}