package com.myyour.eCommApp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.myyour.eCommApp.adapter.ItemAdapter
import com.myyour.eCommApp.Utils.NetworkResult
import com.myyour.eCommApp.Utils.ViewTypes
import com.myyour.eCommApp.databinding.GridViewFragmentBinding
import com.myyour.eCommApp.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Grid view fragment
 *
 * This is Grid View Fragment which inherit the BaseFragment
 *  Its index is One as it associate with second tab in bottom navbar
 *  Its viewtype is ViewTypes.GRIDVIEW -> 1
 *  It uses the ItemAdapter to render the recycler view and passes the GRIDVIEW in viewType
 */
@AndroidEntryPoint
class GridViewFragment : BaseFragment() {
    private val mProductViewModel: ProductViewModel by activityViewModels()
    private lateinit var mBinding: GridViewFragmentBinding
    override lateinit var fragmentView: View
    override fun swipeLeftCallBack() {
        mProductViewModel.swipeLeft(1)
    }

    override fun swipeRightCallBack() {
        mProductViewModel.swipeRight(1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = GridViewFragmentBinding.inflate(inflater, container, false)
        return (mBinding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gridLayoutManager = GridLayoutManager(activity, 3)
        fragmentView = mBinding.itemRecyclerView
        val viewType : ViewTypes = ViewTypes()
        viewType.setViewType(ViewTypes.GRIDVIEW)

        mProductViewModel.products.observe(viewLifecycleOwner) {
            mBinding.loaderRegion.root.visibility = View.GONE
            when (it) {
                is NetworkResult.Loading -> {
                    mBinding.loaderRegion.root.visibility = View.VISIBLE
                }
                is NetworkResult.Loaded -> {
                    mBinding.itemRecyclerView.layoutManager = gridLayoutManager
                    val itemList = it.data!!
                    mBinding.itemRecyclerView.adapter = ItemAdapter(itemList,viewType)
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