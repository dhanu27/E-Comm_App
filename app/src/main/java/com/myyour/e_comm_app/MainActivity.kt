package com.myyour.e_comm_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.myyour.e_comm_app.R.id.fragment_nav_container

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val itemRecyclerView :RecyclerView = findViewById(R.id.itemRecyclerView)
//        val linearLayoutManager = LinearLayoutManager(this)
//        val data:ArrayList<String> = fetchData()
//
//        itemRecyclerView.layoutManager = linearLayoutManager
//        itemRecyclerView.adapter = ItemAdapter(data)
//        itemRecyclerView.addItemDecoration(DividerItemDecoration(baseContext,linearLayoutManager.orientation))

        val navHostFragment =  supportFragmentManager.findFragmentById(R.id.fragment_nav_container) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigation : BottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_container)
        setupWithNavController(bottomNavigation,navController)
    }

}