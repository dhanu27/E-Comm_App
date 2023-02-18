package com.myyour.e_comm_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.myyour.e_comm_app.databinding.FragmentGridViewBinding
import com.myyour.e_comm_app.databinding.FragmentLinearViewBinding
import com.myyour.e_comm_app.enums.VIEWTYPE
import com.myyour.e_comm_app.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentGridView : Fragment() {
    private val productViewModel: ProductViewModel by activityViewModels()
    private lateinit var binding: FragmentGridViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGridViewBinding.inflate(inflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gridLayoutManager = GridLayoutManager(activity, 3)
        binding.itemRecyclerView.layoutManager = gridLayoutManager
        productViewModel.products?.observe(requireActivity()) {
            productViewModel.products.value?.let {
                binding.itemRecyclerView.adapter = ItemAdapter(it, VIEWTYPE.GRIDVIEW)
                binding.itemRecyclerView.addItemDecoration(
                    DividerItemDecoration(
                        activity,
                        gridLayoutManager.orientation
                    )
                )
            }
        }
    }
}