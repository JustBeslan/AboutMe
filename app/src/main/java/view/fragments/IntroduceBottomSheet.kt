package view.fragments

import about.me.R
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import view.fragments.introduce.CurrentLocationFragment
import view.fragments.introduce.DateOfBirthFragment
import view.fragments.introduce.GenderFragment
import view.fragments.introduce.NameFragment
import viewModel.IntroduceBottomSheetAdapter
import kotlin.math.abs

class IntroduceBottomSheet: BottomSheetDialogFragment() {

    companion object {
        const val TAG = "IntroduceBottomSheet"
    }

    private lateinit var viewPager2: ViewPager2
    private lateinit var helperTitleTextView: TextView
    private lateinit var helperTitles: List<String>
    private val listPages = listOf(
        NameFragment(),
        GenderFragment(),
        DateOfBirthFragment(),
        CurrentLocationFragment(),
        NameFragment(),
        NameFragment()
    )

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottom_sheet_introduce_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        helperTitleTextView = view.findViewById(R.id.helperTitleTextView)
        helperTitles = resources.getStringArray(R.array.helperTitles).asList()

        viewPager2 = view.findViewById(R.id.introduceViewPager2)

        viewPager2.offscreenPageLimit = 1
        viewPager2.adapter = IntroduceBottomSheetAdapter(this, listPages)

        val nextItemVisiblePx = resources.getDimension(R.dimen.viewPagerNextItemVisible)
        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewPagerCurrentItemHorizontalMargin)

        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page, position ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.25f * abs(position))
        }
        viewPager2.setPageTransformer(pageTransformer)

        val itemDecoration = HorizontalMarginItemDecoration(
            R.dimen.viewPagerCurrentItemHorizontalMargin
        )
        viewPager2.addItemDecoration(itemDecoration)
        viewPager2.registerOnPageChangeCallback(ViewPager2PageChangeCallback())
    }

    private inner class ViewPager2PageChangeCallback: ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {}

        override fun onPageSelected(position: Int) {
            helperTitleTextView.text =
                when (position) {
                    0 -> helperTitles[0]
                    1 -> helperTitles[1]
                    2 -> helperTitles[2]
                    3 -> helperTitles[3]
                    else -> helperTitles[0]
            }

            if (position == 0) return

            val pastPosition = position - 1
            when (listPages[pastPosition]) {
                is NameFragment -> (listPages[pastPosition] as NameFragment).validate {
                    Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
                    viewPager2.currentItem = if (it) position else pastPosition
                }
                is GenderFragment -> (listPages[pastPosition] as GenderFragment).validate {
                    Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
                    viewPager2.currentItem = if (it) position else pastPosition
                }
                is DateOfBirthFragment -> (listPages[pastPosition] as DateOfBirthFragment).validate()
            }
        }
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