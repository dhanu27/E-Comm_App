package com.myyour.e_comm_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.myyour.e_comm_app.viewmodel.ProductViewModel


abstract class BaseFragment<VM : ViewModel>() : Fragment() {

    @get:LayoutRes
    protected abstract val layoutId: Int

    protected abstract val viewModel: VM

    protected abstract var fragmentView: View
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = DataBindingUtil.inflate(
//            layoutInflater,
//            layoutId,
//            container,
//            false
//        )
//
//        binding.apply {
//            lifecycleOwner = viewLifecycleOwner
//            setVariable(BR.viewModel, viewModel)
//        }
//
//        return binding.root
//    }

    private fun swipeObservation(){
        fragmentView.setOnTouchListener(object: OnSwipeTouchListener(requireContext()) {
            override fun onSwipeLeft() {
//                if(viewModel is ProductViewModel) {
//                    viewModel.swipeLeft(layoutId)
//                }
            }
            override fun onSwipeRight() {
//                viewModel.swipeRight(layoutId)
            }
        })
    }

}