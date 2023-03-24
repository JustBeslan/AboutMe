package viewModel.fragments

import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import view.fragments.ExitBottomSheet
import view.fragments.IntroduceBottomSheet

@BindingAdapter("android:onClick")
fun setOnClick(button: Button, listener: ()->Unit) {
    button.setOnClickListener{ listener() }
}

class WelcomeBottomSheetViewModel(private val contract: WelcomeBottomSheetContract): ViewModel() {

    interface WelcomeBottomSheetContract {
        fun getFragmentManager(): FragmentManager
    }

    companion object {
        private const val COLLAPSED_HEIGHT = 228

        class WelcomeBottomSheetFactory(private val contract: WelcomeBottomSheetContract)
            : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {

                if (modelClass.isAssignableFrom(WelcomeBottomSheetViewModel::class.java))
                    return WelcomeBottomSheetViewModel(contract) as T
                throw java.lang.IllegalArgumentException("Unknown ViewModel class")
            } }
    }

    private var bottomSheetCallback = object: BottomSheetBehavior.BottomSheetCallback() {

        lateinit var layoutCollapsed: LinearLayout
        lateinit var layoutExpanded: LinearLayout

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

    fun configureBehavior(behavior: BottomSheetBehavior<View>,
                          density: Float,
                          layoutCollapsed: LinearLayout,
                          layoutExpanded: LinearLayout) {
        behavior.apply {
            peekHeight = (COLLAPSED_HEIGHT * density).toInt()
            state = BottomSheetBehavior.STATE_COLLAPSED
            addBottomSheetCallback(bottomSheetCallback.apply {
                this.layoutCollapsed = layoutCollapsed
                this.layoutExpanded = layoutExpanded
            })
        }
    }

    fun showExitBottomSheet() {
        ExitBottomSheet().show(contract.getFragmentManager(), ExitBottomSheet.TAG)
    }

    fun showIntroduceBottomSheet() {
        val introduceBottomSheet = IntroduceBottomSheet()
        introduceBottomSheet.isCancelable = false
        introduceBottomSheet.show(contract.getFragmentManager(), IntroduceBottomSheet.TAG)
    }
}