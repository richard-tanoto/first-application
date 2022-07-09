package com.richard.firstapplication.ui.firstscreen

import android.app.ActionBar
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import com.richard.firstapplication.databinding.DialogPalindromeBinding
import com.richard.firstapplication.databinding.FragmentFirstScreenBinding

class FirstScreenFragment : Fragment() {

    private lateinit var binding: FragmentFirstScreenBinding
    private val viewModel: FirstScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFirstScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).window.statusBarColor =
            ContextCompat.getColor(view.context, R.color.aqua_700)

        binding.btnFirstCheck.setOnClickListener {
            val palindrome = binding.edFirstPalindrome.text.toString()
            if (palindrome.isEmpty()) {
                binding.edFirstPalindrome.error = resources.getString(R.string.palindrome_is_empty)
            } else {
                binding.edFirstPalindrome.error = null
                viewModel.checkPalindrome(palindrome)
            }
            viewModel.isPalindrome.observe(viewLifecycleOwner, Observer(this::showDialog))
        }

        binding.btnFirstNext.setOnClickListener {
            val name = binding.edFirstName.text.toString()
            if (name.isEmpty()) {
                binding.edFirstName.error = resources.getString(R.string.name_is_empty)
            } else {
                binding.edFirstName.error = null
                val toSecondScreenFragment =
                    FirstScreenFragmentDirections.actionFirstScreenFragmentToSecondScreenFragment(name)
                view.findNavController().navigate(toSecondScreenFragment)
            }
        }
    }

    private fun showDialog(isPalindrome: Boolean) {
        val dialogBinding = DialogPalindromeBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())
        dialog.apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(dialogBinding.root)
            window?.setLayout(
                (resources.displayMetrics.widthPixels * 0.6).toInt(),
                ActionBar.LayoutParams.WRAP_CONTENT
            )
        }
        dialogBinding.btnDialogOk.setOnClickListener {
            dialog.hide()
        }
        if (isPalindrome) {
            dialogBinding.tvDialogValidation.text = resources.getString(R.string.is_palindrome)
        } else {
            dialogBinding.tvDialogValidation.text = resources.getString(R.string.not_palindrome)
        }
        dialog.show()
    }
}