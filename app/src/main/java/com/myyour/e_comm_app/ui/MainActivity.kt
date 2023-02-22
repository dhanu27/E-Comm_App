package com.myyour.e_comm_app.ui

import android.os.Bundle
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.myyour.e_comm_app.R
import com.myyour.e_comm_app.databinding.ActivityMainBinding
import com.myyour.e_comm_app.databinding.HeaderViewBinding
import com.myyour.e_comm_app.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mProductViewModel: ProductViewModel by viewModels()
    private lateinit var mHeaderViewBinding: HeaderViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // To hide title bar
        supportActionBar?.hide()
        val mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_nav_container) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigation: BottomNavigationView =
            findViewById<BottomNavigationView>(R.id.bottom_nav_container)
        setupWithNavController(bottomNavigation, navController)
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

}