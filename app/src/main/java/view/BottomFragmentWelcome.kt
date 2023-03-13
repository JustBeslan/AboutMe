package view

import about.me.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomFragmentWelcome: BottomSheetDialogFragment() {

    companion object {
        private val COLLAPSED_HEIGHT = 228
    }

    private lateinit var layoutCollapsed: LinearLayout
    private lateinit var layoutExpanded: LinearLayout

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.bottom_sheet_welcome_layout, container, false)

        layoutCollapsed = binding.findViewById(R.id.layout_collapsed)
        layoutExpanded = binding.findViewById(R.id.layout_expanded)

        val supportFragmentManager = requireActivity().supportFragmentManager

        binding.findViewById<Button>(R.id.exitButton).setOnClickListener {
            BottomFragmentExit().show(supportFragmentManager, "userExit")
        }

        binding.findViewById<Button>(R.id.goodButton).setOnClickListener {
            this.dismiss()
            val bottomFragmentBaseInfo = BottomFragmentBaseInfo()
            bottomFragmentBaseInfo.isCancelable = false
            bottomFragmentBaseInfo.show(supportFragmentManager, "baseInfo")
        }

        return binding
    }

    override fun onStart() {
        super.onStart()

        val density = requireContext().resources.displayMetrics.density

        dialog?.let {
            val bottomSheet = it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)

            behavior.peekHeight = (COLLAPSED_HEIGHT * density).toInt()
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED

            behavior.addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {

                override fun onStateChanged(bottomSheet: View, newState: Int) {}

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    if (slideOffset > 0) {
                        layoutCollapsed.alpha = 1 - 2 * slideOffset
                        layoutExpanded.alpha = slideOffset * slideOffset

                        if (slideOffset > 0.5) {
                            layoutCollapsed.visibility = View.GONE
                            layoutExpanded.visibility = View.VISIBLE
                        }

                        if (slideOffset < 0.5 && layoutExpanded.visibility == View.VISIBLE) {
                            layoutCollapsed.visibility = View.VISIBLE
                            layoutExpanded.visibility = View.INVISIBLE
                        }
                    }
                }
            })
        }
    }
}