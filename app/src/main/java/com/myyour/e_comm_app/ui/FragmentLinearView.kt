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
import com.myyour.e_comm_app.adapter.ItemAdapter
import com.myyour.e_comm_app.Utils.NetworkResult
import com.myyour.e_comm_app.databinding.FragmentLinearViewBinding
import com.myyour.e_comm_app.Utils.enums.VIEWTYPE
import com.myyour.e_comm_app.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FragmentLinearView : Fragment() {
    private val mProductViewModel: ProductViewModel by activityViewModels()
    private lateinit var mBinding: FragmentLinearViewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentLinearViewBinding.inflate(inflater, container, false)
        return (mBinding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(activity)
        val itemDivider = DividerItemDecoration(
            activity,
            linearLayoutManager.orientation
        )

        mProductViewModel.products.observe(viewLifecycleOwner, Observer {
            mBinding.loaderRegion.root.visibility = View.GONE
            when (it) {
                is NetworkResult.Loading -> {
                    mBinding.loaderRegion.root.visibility = View.VISIBLE
                }
                is NetworkResult.Loaded -> {
                    mBinding.itemRecyclerView.layoutManager = linearLayoutManager
                    val itemList = it.data ?: emptyList();
                    mBinding.itemRecyclerView.adapter = ItemAdapter(itemList, VIEWTYPE.LINEARVIEW)
//                    mBinding.itemRecyclerView.removeItemDecoration(itemDivider)
//                    mBinding.itemRecyclerView.addItemDecoration(
//                        itemDivider
//                    )
                }
                is NetworkResult.Error -> {
                    mBinding.errorRegion.errorText.text = it.msg
                }
            }
        })

        mBinding.itemRecyclerView.setOnTouchListener(object: OnSwipeTouchListener(context) {
            override fun onSwipeLeft() {
                super.onSwipeLeft()
                mProductViewModel.swipeLeft(0)
            }
            override fun onSwipeRight() {
                super.onSwipeRight()
                mProductViewModel.swipeRight(0)
            }
        })


    }
}