package com.richard.firstapplication.ui.thirdscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.richard.firstapplication.R
import com.richard.firstapplication.adapter.UserAdapter
import com.richard.firstapplication.data.response.UserItem
import com.richard.firstapplication.databinding.FragmentThirdScreenBinding

class ThirdScreenFragment : Fragment() {

    private lateinit var binding: FragmentThirdScreenBinding
    private val viewModel: ThirdScreenViewModel by viewModels {
        ViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentThirdScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBar()
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val userAdapter = UserAdapter()
        binding.rvListUser.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = userAdapter
            addItemDecoration(
                DividerItemDecoration(
                    binding.rvListUser.context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        viewModel.users.observe(viewLifecycleOwner) {
            userAdapter.submitData(lifecycle, it)

            // Show empty text if the list is empty
            userAdapter.addLoadStateListener { loadStates ->
                if (loadStates.append.endOfPaginationReached) {
                    binding.tvThirdEmpty.visibility = if (userAdapter.itemCount == 0) View.VISIBLE else View.INVISIBLE
                }
            }
        }

        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserItem) {
                requireView().findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    "key",
                    data.firstName + " " + data.lastName
                )
                requireView().findNavController().popBackStack()
            }
        })

        binding.swrThirdList.setOnRefreshListener {
            userAdapter.refresh()
            binding.swrThirdList.isRefreshing = false
        }
    }

    private fun setUpBar() {
        (activity as AppCompatActivity).apply {
            window.statusBarColor =
                ContextCompat.getColor(requireView().context, R.color.transparent)
            setSupportActionBar(requireView().findViewById(R.id.third_screen_toolbar))
            supportActionBar?.apply {
                title = null
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.ic_arrow_left)
            }
        }
    }

}