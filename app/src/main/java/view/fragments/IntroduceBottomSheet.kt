package view.fragments

import about.me.R
import about.me.databinding.BottomSheetIntroduceBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import viewModel.fragments.IntroduceBottomSheetViewModel

class IntroduceBottomSheet: BottomSheetDialogFragment() {

    companion object {
        const val TAG = "IntroduceBottomSheet"
    }

    private lateinit var binding: BottomSheetIntroduceBinding
    private val viewModel: IntroduceBottomSheetViewModel by viewModels {
        IntroduceBottomSheetViewModel.Companion.IntroduceBottomSheetFactory (
            object: IntroduceBottomSheetViewModel.IntroduceBottomSheetContract {
                override fun getLifecycle() = lifecycle
                override fun getContext() = requireContext()
                override fun getFragmentManager() = childFragmentManager
            }
        )
    }

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetIntroduceBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.currentPage.observe(viewLifecycleOwner) {
            binding.viewPager2.currentItem = it
        }
        binding.apply {
            viewPager2.offscreenPageLimit = 1
            introduceBottomSheetViewModel = viewModel
        }
    }
}