package view.fragments.introduce

import about.me.databinding.FragmentNameBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import viewModel.fragments.introduce.NameFragmentViewModel

class NameFragment : Fragment() {

    private lateinit var binding: FragmentNameBinding
    private val viewModel: NameFragmentViewModel by viewModels {
        NameFragmentViewModel.Companion.NameFragmentFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNameBinding.inflate(
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
            nameFragmentViewModel = viewModel
        }
        arguments?.takeIf { it.containsKey("position") }?.apply {
            binding.indicatorPage.text = getString("position")
        }
    }

    fun validate(callback: (Boolean)->Unit) {
        callback(viewModel.isValidData())
    }

}