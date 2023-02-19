package com.myyour.e_comm_app.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.myyour.e_comm_app.ItemAdapter
import com.myyour.e_comm_app.Utils.NetworkResult
import com.myyour.e_comm_app.databinding.FragmentLinearViewBinding
import com.myyour.e_comm_app.Utils.enums.VIEWTYPE
import com.myyour.e_comm_app.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FragmentLinearView : Fragment() {
    private val productViewModel: ProductViewModel by activityViewModels()
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
        val linearLayoutManager = LinearLayoutManager(activity)

        productViewModel.products?.observe(viewLifecycleOwner, Observer {
            binding.loaderRegion.root.visibility = View.GONE;
            when (it) {
                is NetworkResult.Loading -> {
                    binding.loaderRegion.root.visibility = View.VISIBLE;
                }
                is NetworkResult.Loaded -> {
                    binding.itemRecyclerView.layoutManager = linearLayoutManager
                    val itemList = it.data ?: emptyList();
                    binding.itemRecyclerView.adapter = ItemAdapter(itemList, VIEWTYPE.LINEARVIEW)
                    binding.itemRecyclerView.addItemDecoration(
                        DividerItemDecoration(
                            activity,
                            linearLayoutManager.orientation
                        )
                    )
                }
                is NetworkResult.Error -> {
                    binding.errorRegion.errorText.text = it.msg
                }
            }
        })
    }
}