package com.myyour.e_comm_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val itemRecyclerView :RecyclerView = findViewById(R.id.itemRecyclerView)
        val linearLayoutManager = LinearLayoutManager(this)
        val data:ArrayList<String> = fetchData()

        itemRecyclerView.layoutManager = linearLayoutManager
        itemRecyclerView.adapter = ItemAdapter(data)
        itemRecyclerView.addItemDecoration(DividerItemDecoration(baseContext,linearLayoutManager.orientation))
    }

    private fun fetchData(): ArrayList<String> {
        val arr = ArrayList<String>()
        for(item in 0..100){
            arr.add("Item ${item+1}")
        }
       return arr
    }
}