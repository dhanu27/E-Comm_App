package com.myyour.e_comm_app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.myyour.e_comm_app.R
import com.myyour.e_comm_app.databinding.HeaderViewBinding
import com.myyour.e_comm_app.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val productViewModel: ProductViewModel by viewModels<ProductViewModel>()
    private lateinit var headerBinding : HeaderViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide() // To hide title bar
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_nav_container) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigation: BottomNavigationView =
            findViewById<BottomNavigationView>(R.id.bottom_nav_container)
        setupWithNavController(bottomNavigation, navController)
        headerBinding = HeaderViewBinding.inflate(layoutInflater)
    }

    private fun handleSearchBar(){
        val searchEditText = headerBinding.searchTextView
//
//        searchEditText.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(Editable s) {
//
//                // you can call or do what you want with your EditText here
//
//                // yourEditText...
//            }
//
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {}
//        });
    }


}