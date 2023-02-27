package com.myyour.eCommApp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.myyour.eCommApp.adapter.ItemAdapter
import com.myyour.eCommApp.Utils.NetworkResult
import com.myyour.eCommApp.Utils.ViewTypes
import com.myyour.eCommApp.databinding.LinearViewFragmentBinding
import com.myyour.eCommApp.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint


/**
 * Linear view fragment
 *
 *  * This is Linear View Fragment class which inherit from BaseFragment
 *  Its index is Zero as it associate with first  tab in bottom navbar
 *  Its viewtype is ViewTypes.LINEARVIEW -> 0
 *  It uses the ItemAdapter to render the recycler view and passes the LINEARVIEW in viewType
 */
@AndroidEntryPoint
class LinearViewFragment : BaseFragment() {
    private val mProductViewModel: ProductViewModel by activityViewModels()
    private lateinit var mBinding: LinearViewFragmentBinding

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
        mBinding = LinearViewFragmentBinding.inflate(inflater, container, false)
        return (mBinding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(activity)
        fragmentView = mBinding.itemRecyclerView
        val viewType : ViewTypes = ViewTypes()
        viewType.setViewType(ViewTypes.LINEARVIEW)

        mProductViewModel.products.observe(viewLifecycleOwner) {
            mBinding.loaderRegion.root.visibility = View.GONE
            when (it) {
                is NetworkResult.Loading -> {
                    mBinding.loaderRegion.root.visibility = View.VISIBLE
                }
                is NetworkResult.Loaded -> {
                    mBinding.itemRecyclerView.layoutManager = linearLayoutManager
                    val itemList = it.data ?: emptyList()
                    mBinding.itemRecyclerView.adapter = ItemAdapter(itemList, viewType)
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