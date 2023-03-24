package viewModel.fragments

import about.me.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class IntroduceBottomSheetViewPager2Adapter(
    private val contract: IntroduceBottomSheetViewModel.IntroduceBottomSheetContract,
    private val listPages: List<Fragment>
): FragmentStateAdapter(contract.getFragmentManager(), contract.getLifecycle()) {

    override fun getItemCount() = listPages.size

    override fun createFragment(position: Int): Fragment {
        val currentFragment = listPages[position]
        currentFragment.arguments = Bundle().apply {
            putString("position", contract.getContext().getString(R.string.page, position + 1, itemCount))
        }
        return currentFragment
    }
}