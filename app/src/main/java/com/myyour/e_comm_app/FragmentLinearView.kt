package com.myyour.e_comm_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myyour.e_comm_app.databinding.FragmentLinearViewBinding
import com.myyour.e_comm_app.enums.VIEWTYPE
import com.myyour.e_comm_app.model.Item
import com.myyour.e_comm_app.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FragmentLinearView : Fragment() {
    private val productViewModel:ProductViewModel by activityViewModels()
    private lateinit var binding: FragmentLinearViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLinearViewBinding.inflate(inflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val itemRecyclerView: RecyclerView = view.findViewById(R.id.itemRecyclerView)
        val linearLayoutManager = LinearLayoutManager(activity)

        binding.itemRecyclerView.layoutManager = linearLayoutManager

        productViewModel.products?.observe(requireActivity()) {
            productViewModel.products.value?.let {
                binding.itemRecyclerView.adapter = ItemAdapter(it, VIEWTYPE.LINEARVIEW)
                binding.itemRecyclerView.addItemDecoration(
                    DividerItemDecoration(
                        activity,
                        linearLayoutManager.orientation
                    )
                )
            }
        }
    }
}