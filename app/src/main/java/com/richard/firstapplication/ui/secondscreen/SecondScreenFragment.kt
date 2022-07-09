package com.richard.firstapplication.ui.secondscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.richard.firstapplication.R
import com.richard.firstapplication.databinding.FragmentSecondScreenBinding

class SecondScreenFragment : Fragment() {

    private lateinit var binding: FragmentSecondScreenBinding
    private val viewModel: SecondScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSecondScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBar()

        val mName = SecondScreenFragmentArgs.fromBundle(arguments as Bundle).name
        viewModel.setName(mName)

        view.findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("key")?.observe(
            viewLifecycleOwner) { selectedName ->
            if (selectedName == null) {
                viewModel.setSelectedName(resources.getString(R.string.no_user_is_selected))
            } else {
                viewModel.setSelectedName(selectedName)
            }
        }

        viewModel.name.observe(viewLifecycleOwner, Observer(this::setName))

        viewModel.selectedName.observe(viewLifecycleOwner, Observer(this::setSelectedName))

        binding.btnSecondChooseUser.setOnClickListener {
            val toThirdScreenFragment =
                SecondScreenFragmentDirections.actionSecondScreenFragmentToThirdScreenFragment()
            view.findNavController().navigate(toThirdScreenFragment)
        }
    }

    private fun setUpBar() {
        (activity as AppCompatActivity).apply {
            window.statusBarColor =
                ContextCompat.getColor(requireView().context, R.color.transparent)
            setSupportActionBar(requireView().findViewById(R.id.second_screen_toolbar))
            supportActionBar?.apply {
                title = null
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.ic_arrow_left)
            }
        }
    }

    private fun setName(name: String) {
        binding.tvSecondName.text = name
    }

    private fun setSelectedName(selectedName: String) {
        binding.tvSecondSelectedName.text = selectedName
    }
}