package viewModel.fragments

import about.me.R
import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import view.fragments.introduce.DateOfBirthFragment
import view.fragments.introduce.GenderFragment
import view.fragments.introduce.NameFragment
import kotlin.math.abs

@BindingAdapter("itemDecoration")
fun addItemDecoration(viewPager2: ViewPager2, itemDecoration: RecyclerView.ItemDecoration) {
    viewPager2.addItemDecoration(itemDecoration)
}

@BindingAdapter("pageTransformer")
fun setPageTransformer(viewPager2: ViewPager2, pageTransformer: ViewPager2.PageTransformer) {
    viewPager2.setPageTransformer(pageTransformer)
}

@BindingAdapter("pageChangeCallback")
fun setPageTransformer(viewPager2: ViewPager2, pageChangeCallback: ViewPager2.OnPageChangeCallback) {
    viewPager2.registerOnPageChangeCallback(pageChangeCallback)
}

@BindingAdapter("adapter")
fun setAdapter(viewPager2: ViewPager2, adapter: FragmentStateAdapter) {
    viewPager2.adapter = adapter
}

class IntroduceBottomSheetViewModel(contract: IntroduceBottomSheetContract): ViewModel() {

    interface IntroduceBottomSheetContract {
        fun getLifecycle(): Lifecycle
        fun getContext(): Context
        fun getFragmentManager(): FragmentManager
    }
    
    companion object {
        class IntroduceBottomSheetFactory(private val contract: IntroduceBottomSheetContract)
            : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {

                if (modelClass.isAssignableFrom(IntroduceBottomSheetViewModel::class.java))
                    return IntroduceBottomSheetViewModel(contract) as T
                throw java.lang.IllegalArgumentException("Unknown ViewModel class")
            } }
    }

    val itemDecoration = object:  RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val horizontalMarginInPx = parent.resources.getDimension(R.dimen.viewPagerCurrentItemHorizontalMargin).toInt()
            parent.resources
            outRect.right = horizontalMarginInPx
            outRect.left = horizontalMarginInPx
        }
    }

    val pageTransformer = ViewPager2.PageTransformer { page, position ->
        val pageTranslationX = page.resources.getDimension(R.dimen.viewPagerNextItemVisible) +
                page.resources.getDimension(R.dimen.viewPagerCurrentItemHorizontalMargin)
        page.translationX = -pageTranslationX * position
        page.scaleY = 1 - (0.25f * abs(position))
    }

    private val listPages = listOf(
//        CurrentLocationFragment(),
        NameFragment(),
        GenderFragment(),
        DateOfBirthFragment(),
        NameFragment(),
        NameFragment()
    )
    var currentPage = MutableLiveData(0)

    val pageChangeCallback = object: ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            currentPage.value = position
            if (position == 0) return
            val pastPosition = position - 1
            when (listPages[pastPosition]) {
                is NameFragment -> (listPages[pastPosition] as NameFragment).validate {
                    currentPage.value = if (it) position else pastPosition
                }
                is GenderFragment -> (listPages[pastPosition] as GenderFragment).validate {
                    currentPage.value = if (it) position else pastPosition
                }
                is DateOfBirthFragment -> (listPages[pastPosition] as DateOfBirthFragment).validate()
            }
        }
    }

    val adapter = IntroduceBottomSheetViewPager2Adapter(contract, listPages)
}