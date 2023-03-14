package view.fragments

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
import viewModel.WelcomeBottomSheetViewModel

class WelcomeBottomSheet: BottomSheetDialogFragment() {

    companion object {
        const val TAG = "WelcomeBottomSheet"
        private const val COLLAPSED_HEIGHT = 228
    }

    private lateinit var layoutCollapsed: LinearLayout
    private lateinit var layoutExpanded: LinearLayout
    private lateinit var welcomeBottomSheetViewModel: WelcomeBottomSheetViewModel

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottom_sheet_welcome_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        layoutCollapsed = view.findViewById(R.id.layout_collapsed)
        layoutExpanded = view.findViewById(R.id.layout_expanded)

        welcomeBottomSheetViewModel = WelcomeBottomSheetViewModel(requireActivity().supportFragmentManager)
        view.findViewById<Button>(R.id.exitButton).setOnClickListener { welcomeBottomSheetViewModel.showExitBottomSheet() }
        view.findViewById<Button>(R.id.goodButton).setOnClickListener {
            this.dismiss()
            welcomeBottomSheetViewModel.showIntroduceBottomSheet()
        }
    }

    override fun onStart() {
        super.onStart()

        val density = requireContext().resources.displayMetrics.density

        dialog?.let {
            val bottomSheet = it.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            val behavior = BottomSheetBehavior.from(bottomSheet)

            behavior.peekHeight = (COLLAPSED_HEIGHT * density).toInt()
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED

            behavior.addBottomSheetCallback(BottomSheetCallback())
        }
    }

    private inner class BottomSheetCallback: BottomSheetBehavior.BottomSheetCallback() {

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
    }
}