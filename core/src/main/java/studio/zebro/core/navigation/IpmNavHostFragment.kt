package studio.zebro.core.navigation

import android.view.View
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import studio.zebro.core.R

class CustomNavHostFragment : NavHostFragment() {
  override fun createFragmentNavigator() = CustomFragmentNavigator(requireContext(), childFragmentManager, getContainerId())

  /**
   * We specifically can't use [View.NO_ID] as the container ID (as we use
   * [androidx.fragment.app.FragmentTransaction.add] under the hood),
   * so we need to make sure we return a valid ID when asked for the container ID.
   *
   * @return a valid ID to be used to contain child fragments
   */
  private fun getContainerId(): Int {
    val id = id
    return if (id != 0 && id != View.NO_ID) {
      id
    } else R.id.nav_host_fragment_container
    // Fallback to using our own ID if this Fragment wasn't added via
    // add(containerViewId, Fragment)
  }
}