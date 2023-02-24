package com.myyour.e_comm_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.myyour.e_comm_app.adapter.ItemAdapter
import com.myyour.e_comm_app.Utils.NetworkResult
import com.myyour.e_comm_app.databinding.FragmentLinearViewBinding
import com.myyour.e_comm_app.Utils.enums.VIEWTYPE
import com.myyour.e_comm_app.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FragmentLinearView : BaseFragment() {
    private val mProductViewModel: ProductViewModel by activityViewModels()
    private lateinit var mBinding: FragmentLinearViewBinding

    override lateinit var fragmentView: View
    override fun swipeLeftCallBack() {
        mProductViewModel.swipeLeft(0)
    }

    override fun swipeRightCallBack() {
        mProductViewModel.swipeRight(0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentLinearViewBinding.inflate(inflater, container, false)
        return (mBinding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(activity)
        fragmentView = mBinding.itemRecyclerView

        mProductViewModel.products.observe(viewLifecycleOwner) {
            mBinding.loaderRegion.root.visibility = View.GONE
            when (it) {
                is NetworkResult.Loading -> {
                    mBinding.loaderRegion.root.visibility = View.VISIBLE
                }
                is NetworkResult.Loaded -> {
                    mBinding.itemRecyclerView.layoutManager = linearLayoutManager
                    val itemList = it.data ?: emptyList()
                    mBinding.itemRecyclerView.adapter = ItemAdapter(itemList, VIEWTYPE.LINEARVIEW)
                }
                is NetworkResult.Error -> {
                    mBinding.errorRegion.errorText.text = it.msg
                }
            }
        }
        swipeObservation()

        mBinding.swipeRefresh.setOnRefreshListener {
            mProductViewModel.refreshProductList()
        }
        mProductViewModel.isRefreshing.observe(viewLifecycleOwner) {
            mBinding.swipeRefresh.isRefreshing = it
        }

    }
}