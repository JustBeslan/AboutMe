package viewModel

import about.me.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class IntroduceBottomSheetAdapter(
    private val fragment: Fragment,
    private val listFragments: List<Fragment>
): FragmentStateAdapter(fragment) {

    override fun getItemCount() = listFragments.size

    override fun createFragment(position: Int): Fragment {
        val currentFragment = listFragments[position]
        currentFragment.arguments = Bundle().apply {
            putString("position", fragment.requireContext().getString(R.string.page, position + 1, itemCount))
        }
        return currentFragment
    }

}