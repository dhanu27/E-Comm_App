package com.myyour.e_comm_app.ui

import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.allViews
import androidx.core.view.forEach
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.myyour.e_comm_app.R
import com.myyour.e_comm_app.Utils.Constants.routesList
import com.myyour.e_comm_app.Utils.enums.SwipeGestures
import com.myyour.e_comm_app.databinding.ActivityMainBinding
import com.myyour.e_comm_app.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mProductViewModel: ProductViewModel by viewModels()
    private lateinit var mainBinding: ActivityMainBinding
    private var menuItemsArray : ArrayList<MenuItem> = ArrayList();
    private lateinit var navController: NavController;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // To hide title bar
        supportActionBar?.hide()
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_nav_container) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigation: BottomNavigationView =
            findViewById<BottomNavigationView>(R.id.bottom_nav_container)
        setupWithNavController(bottomNavigation, navController)
        mainBinding.bottomNavContainer.menu.forEach { item -> menuItemsArray.add(item) }
        handleSearchBar()
        observeFragmentSwipeGestures()
    }

    private fun handleSearchBar() {
        mainBinding.headerSectionView.searchName.setOnQueryTextListener(object :
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
            val index: Int = it.first;
            when(it.second){
                SwipeGestures.Right -> {
                    if (index > 0) {
                        handleNavigation(menuItemsArray[index - 1])
                    }
                }
                SwipeGestures.Left ->{
                    if (index < menuItemsArray.size - 1) {
                        handleNavigation(menuItemsArray[index + 1])
                    }
                }
            }
        }
    }



    private fun handleNavigation(id: MenuItem) {
        NavigationUI.onNavDestinationSelected(id, navController )
    }

}