package viewModel

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import view.baseInfo.NameFragment

class BaseInfoAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount() = 5

    override fun createFragment(position: Int): Fragment {
        val fragment = NameFragment()
        fragment.arguments = Bundle().apply {
            putInt("position", position + 1)
        }
        return fragment
    }
}