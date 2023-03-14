package viewModel

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import view.fragments.ExitBottomSheet
import view.fragments.IntroduceBottomSheet

class WelcomeBottomSheetViewModel(
    private val fragmentManager: FragmentManager
): ViewModel() {

    fun showExitBottomSheet() {
        ExitBottomSheet().show(fragmentManager, ExitBottomSheet.TAG)
    }

    fun showIntroduceBottomSheet() {
        val introduceBottomSheet = IntroduceBottomSheet()
        introduceBottomSheet.isCancelable = false
        introduceBottomSheet.show(fragmentManager, IntroduceBottomSheet.TAG)
    }
}