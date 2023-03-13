package view

import about.me.R
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import viewModel.BaseInfoAdapter
import kotlin.math.abs

class BottomFragmentBaseInfo: BottomSheetDialogFragment() {

    private lateinit var viewPager2: ViewPager2

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = inflater.inflate(R.layout.bottom_sheet_base_info_layout, container, false)

        viewPager2 = binding.findViewById(R.id.baseInfoViewPager2)

        viewPager2.offscreenPageLimit = 1

        val nextItemVisiblePx = resources.getDimension(R.dimen.viewPagerNextItemVisible)
        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewPagerCurrentItemHorizontalMargin)

        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page, position ->
            Toast.makeText(context, "position $position", Toast.LENGTH_SHORT).show()
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.25f * abs(position))
        }
        viewPager2.setPageTransformer(pageTransformer)

        val itemDecoration = HorizontalMarginItemDecoration(
            R.dimen.viewPagerCurrentItemHorizontalMargin
        )
        viewPager2.addItemDecoration(itemDecoration)

        viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {

            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}

            override fun onPageSelected(position: Int) {}
        })
        return binding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        val adapter = BaseInfoAdapter(this)
        viewPager2.adapter = BaseInfoAdapter(this)
    }

    private inner class HorizontalMarginItemDecoration(
        @DimenRes horizontalMarginInDp: Int
    ): RecyclerView.ItemDecoration() {

        private val horizontalMarginInPx: Int = resources.getDimension(horizontalMarginInDp).toInt()

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.right = horizontalMarginInPx
            outRect.left = horizontalMarginInPx
        }
    }
}