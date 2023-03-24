package view.fragments

import about.me.R
import about.me.databinding.BottomSheetWelcomeBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import viewModel.fragments.WelcomeBottomSheetViewModel

class WelcomeBottomSheet: BottomSheetDialogFragment() {

    companion object {
        const val TAG = "WelcomeBottomSheet"
    }

    private lateinit var binding: BottomSheetWelcomeBinding
    private val viewModel: WelcomeBottomSheetViewModel by viewModels {
        WelcomeBottomSheetViewModel.Companion.WelcomeBottomSheetFactory(
            object: WelcomeBottomSheetViewModel.WelcomeBottomSheetContract {
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
        binding = BottomSheetWelcomeBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            welcomeBottomSheetViewModel = viewModel
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            viewModel.configureBehavior(
                BottomSheetBehavior.from(requireView().parent as View),
                requireContext().resources.displayMetrics.density,
                binding.layoutCollapsed,
                binding.layoutExpanded
            )
        }
    }
}