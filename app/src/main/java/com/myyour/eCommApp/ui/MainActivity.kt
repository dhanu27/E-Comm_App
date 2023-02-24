package com.myyour.eCommApp.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.myyour.eCommApp.R
import com.myyour.eCommApp.Utils.enums.SwipeGestures
import com.myyour.eCommApp.databinding.ActivityMainBinding
import com.myyour.eCommApp.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mProductViewModel: ProductViewModel by viewModels()
    private lateinit var mMainActivityBinding: ActivityMainBinding
    private var mMenuItemsArray : ArrayList<MenuItem> = ArrayList()
    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // To hide title bar
        supportActionBar?.hide()
        mMainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mMainActivityBinding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_nav_container) as NavHostFragment
        mNavController = navHostFragment.navController
        val bottomNavigation: BottomNavigationView =
            findViewById(R.id.bottom_nav_container)
        setupWithNavController(bottomNavigation, mNavController)
        mMainActivityBinding.bottomNavContainer.menu?.forEach { item -> mMenuItemsArray.add(item) }
        handleSearchBar()
        observeFragmentSwipeGestures()
    }

    private fun handleSearchBar() {
        mMainActivityBinding.headerSectionView.searchName.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mProductViewModel.getProductListOnSearch(newText ?: "")
                return true
            }
        })
    }

    private fun observeFragmentSwipeGestures() {
        mProductViewModel.swipeGesture.observe(this) {
            val index: Int = it.first
            when(it.second){
                SwipeGestures.Right -> {
                    if (index > 0) {
                        handleNavigation(mMenuItemsArray[index - 1])
                    }
                }
                SwipeGestures.Left -> {
                    if (index < mMenuItemsArray.size - 1) {
                        handleNavigation(mMenuItemsArray[index + 1])
                    }
                }
            }
        }
    }



    private fun handleNavigation(id: MenuItem) {
        NavigationUI.onNavDestinationSelected(id, mNavController )
    }

}