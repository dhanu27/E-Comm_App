package com.myyour.e_comm_app.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.myyour.e_comm_app.ItemAdapter
import com.myyour.e_comm_app.Utils.NetworkResult
import com.myyour.e_comm_app.databinding.FragmentGridViewBinding
import com.myyour.e_comm_app.Utils.enums.VIEWTYPE
import com.myyour.e_comm_app.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentGridView : Fragment() {
    private val productViewModel: ProductViewModel by activityViewModels()
    private lateinit var binding: FragmentGridViewBinding

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

        productViewModel.products?.observe(viewLifecycleOwner, Observer{
            binding.loaderRegion.root.visibility = View.GONE;
            when(it){
                is NetworkResult.Loading ->{
                    binding.loaderRegion.root.visibility = View.VISIBLE;
                }
                is NetworkResult.Loaded ->{
                    binding.itemRecyclerView.layoutManager = gridLayoutManager
                    val itemList = it.data!!;
                    binding.itemRecyclerView.adapter = ItemAdapter(itemList, VIEWTYPE.GRIDVIEW)
                }
                is NetworkResult.Error ->{
                    binding.errorRegion.errorText.text = it.msg
                }
            }
        })
    }
}